<template>
  <div>
    <button type="button" v-on:click="selectFile()" class="btn btn-white btn-default btn-round">
      <i class="ace-icon fa fa-edit"></i>
      {{text}}
    </button>
    <input class="hidden" type="file" ref="file" v-on:change="uploadFile()" v-bind:id="inputId+'-input'">

  </div>

</template>

<script>
  export default {
    name: 'file',
    // props里面可以理解为:这个组件需要暴露出去配置的一些属性
    props: {
      text: {
        default: "上传文件"
      },
      inputId: {
        default: "file-upload"
      },
      suffixes: {
        default: []
      },
      use: {
        default: ""
      },
      afterUpload: {
        type: Function,
        default: null
      },
    },
    data: function () {
      return {
      }
    },
    methods: {

      /**
       * 图片上传
       */
      uploadFile() {
        let _this = this;
        let file = _this.$refs.file.files[0];

        // 文件上传格式的校验判断
        let suffixes = _this.suffixes;
        let fileName = file.name;
        let suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length).toLowerCase();
        let validateSuffix = false;
        for (let i = 0; i < suffixes.length; i++) {
          if (suffixes[i].toLowerCase() === suffix) {
            validateSuffix = true;
            break;
          }
        }
        if(!validateSuffix) {
          Toast.warning("文件格式不正确！只支持上传：" + suffixes.join(","));
          // 清空组件里面的东西，防止下次若上传一样的文件时，无法触发v-on:change="uploadFile()"
          $("#" + _this.inputId + "-input").val("");
          return;
        }

        // 生成文件的标识，利用md5信息摘要算法
        let key = hex_md5(file); // 是16进制的
        let key10 = parseInt(key, 16); //转成10进制
        let key62 = Tool._10to62(key10); //转成62进制，缩短字符串长度
        // 文件分片
        let shardSize = 10 * 1024 * 1024; // 10MB
        let shardIndex = 1; // 分片序号, 从1开始，1表示第一条数据
        let size = file.size;
        let shardTotal = Math.ceil(size / shardSize); // 总片数

        let param = {
          'shardIndex': shardIndex,
          'shardSize': shardSize,
          'shardTotal': shardTotal,
          'use': _this.use,
          'name': file.name,
          'suffix': suffix,
          'size': file.size,
          'key': key62
        };
        _this.check(param);
      },


      /**
       * 检查文件状态，是否已上传过？传到第几个分片？
       */
      check (param) {
        let _this = this;
        _this.$ajax.get(process.env.VUE_APP_SERVER + '/file/admin/check/' + param.key).then((response)=>{
          let resp = response.data;
          if (resp.success) { // check返回成功
            let obj = resp.content;
            if (!obj) {
              param.shardIndex = 1;
              console.log("没有找到文件记录，从分片1开始上传");
              _this.uploadShard(param);
            } else if (obj.shardIndex === obj.shardTotal) {
              // 已上传分片 = 分片总数，说明已全部上传完，不需要再上传
              Toast.success("文件极速秒传成功！");
              _this.afterUpload(resp);
              $("#" + _this.inputId + "-input").val("");
            }  else {
              param.shardIndex = obj.shardIndex + 1;
              console.log("找到文件记录，从分片" + param.shardIndex + "开始上传");
              _this.uploadShard(param);
            }
          } else { // check返回失败
            Toast.warning("文件上传失败");
            $("#" + _this.inputId + "-input").val("");
          }
        })
      },


      /**
       * 连续上传分片的递归辅助方法
      */
       uploadShard: function (param) {
        let _this = this;
        let shardIndex = param.shardIndex;
        let shardTotal = param.shardTotal;
        let shardSize = param.shardSize;
        let fileShard = _this.getFileShard(shardIndex, shardSize);
        // 显示进度条
        Progress.show(parseInt((shardIndex - 1) * 100 / shardTotal));
        // 将文件类型转成base64进行传输
        let fileReader = new FileReader();
        fileReader.onload = function (e) {
          let base64 = e.target.result;
          param.shard = base64;

          _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', param).then((response) => {
            let resp = response.data;
            // 显示进度条
            Progress.show(parseInt((shardIndex - 1) * 100 / shardTotal));
            if (shardIndex < shardTotal) {
              // 递归的继续上传下一个分片
              param.shardIndex = param.shardIndex + 1;
              _this.uploadShard(param);
            } else {
              console.log("上传文件成功：", resp);
              // 取消显示进度条
              Progress.hide();
              _this.afterUpload(resp);
              // 清空组件里面的东西，防止下次若上传一样的文件时，无法触发v-on:change="uploadFile()"
              $("#" + _this.inputId + "-input").val("");
            }
          });
        };
        fileReader.readAsDataURL(fileShard);
      },


      getFileShard: function (shardIndex, shardSize) {
        let _this = this;
        let file = _this.$refs.file.files[0];
        let start = (shardIndex - 1) * shardSize;
        let end = Math.min(start + shardSize, file.size);
        let fileShard = file.slice(start, end); // 从文件中截取当前分片数据
        return fileShard;
      },


      /**
       * 点击上传图片按钮，触发uploadFile()方法
       */
      selectFile() {
        let _this = this;
        $("#" + _this.inputId + "-input").trigger("click");
      }
    }
  }
</script>
