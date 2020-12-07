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
        let formData = new window.FormData();
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

        formData.append('file', file);
        formData.append('use', _this.use);
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/file/admin/upload', formData).then((response) =>{
          Loading.hide();
          let resp = response.data;
          console.log("上传文件成功：", resp);
          _this.afterUpload(resp);
          // 清空组件里面的东西，防止下次若上传一样的文件时，无法触发v-on:change="uploadFile()"
          $("#" + _this.inputId + "-input").val("");
        });
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
