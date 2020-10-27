<template>
  <div>
    <h4 class="lighter">
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      <router-link to="/business/course" class="pink"> {{course.name}} </router-link>
      <i class="ace-icon fa fa-hand-o-right icon-animated-hand-pointer blue"></i>
      大章管理：
    </h4>
    <hr>

    <p>
      <router-link to="/business/course" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-arrow-left"></i>
        返回课程
      </router-link>
      &nbsp;
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
      &nbsp;
      <button v-on:click="list()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
    </p>

    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>

        <th>ID</th>
        <th>名称</th>
        <th>课程</th>
        <th>操作</th>

      </tr>
      </thead>

      <tbody>

      <tr v-for="chapter in chapters">
        <td>{{chapter.id}}</td>
        <td>{{chapter.name}}</td>
        <td>{{course.name}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="toSection(chapter)" class="btn btn-xs btn-info">
              小节
            </button>
            <button v-on:click="edit(chapter)" class="btn btn-xs btn-info">
              编辑
            </button>
            <button v-on:click= "del(chapter.id)" class="btn btn-xs btn-danger">
              删除
            </button>
          </div>

          <div class="hidden-md hidden-lg">
            <div class="inline pos-rel">
              <button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
                <i class="ace-icon fa fa-cog icon-only bigger-110"></i>
              </button>

              <ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
                <li>
                  <a href="#" class="tooltip-info" data-rel="tooltip" title="View">
                    <span class="blue">
                      <i class="ace-icon fa fa-search-plus bigger-120"></i>
                    </span>
                  </a>
                </li>

                <li>
                  <a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
                    <span class="green">
                      <i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
                    </span>
                  </a>
                </li>

                <li>
                  <a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
                    <span class="red">
                      <i class="ace-icon fa fa-trash-o bigger-120"></i>
                    </span>
                  </a>
                </li>
              </ul>
            </div>
          </div>
        </td>
      </tr>

      </tbody>
    </table>

    <!--  模态框  -->
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">表单</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input v-model="chapter.name" class="form-control" placeholder="名称">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程</label>
                <div class="col-sm-10">
                  <p class="form-control-static">{{course.name}}</p>
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button v-on:click="save()" type="button" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <!--  分页组件  -->
    <pagination ref="pagination" v-bind:list="list" style=""></pagination>
  </div>
</template>

<script>
  import Pagination from "../../components/pagination";
  export default {
    name: 'chapter',
    components: {Pagination},
    data: function() {
      return {
        chapter: {},
        chapters: [],
        course: {},
      }
    },
    mounted: function() {
      // sidebar激活样式方法一
      // this.$parent.activeSidebar("business-chapter-sidebar");
      let _this = this;
      let course = SessionStorage.get(SESSION_KEY_COURSE) || {};
      if (Tool.isEmpty(course)) {
        // 如果直接访问大章页面，将其跳转回welcome页面
        _this.$router.push("/welcome");
      }
      _this.course = course;
      _this.list(1);
    },
    methods: {
      /**
       *  增加大章
       */
      add(){
        let _this = this;
        // 不受编辑框的影响
        _this.chapter = {};
        $("#form-modal").modal("show");
      },
      /**
       *  编辑大章
       */
      edit(chapter){
        let _this = this;
        // 复制chapter，免得影响到表格里面的值
        _this.chapter = $.extend({}, chapter);
        $("#form-modal").modal("show");
      },
      /**
       * 列表查询
       */
      list(page) {
        let _this = this;
        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/chapter/list', {
          page: page,
          size: _this.$refs.pagination.size,
          courseId: _this.course.id,
        }).then((response)=>{
          Loading.hide();
          let resp = response.data;
          _this.chapters = resp.content.list;
          _this.$refs.pagination.render(page, resp.content.total);
        })
      },
      /**
       * 点击【保存】：新增和修改
       */
      save() {
        let _this = this;

        // 保存校验
        if (!Validator.require(_this.chapter.name, "名称")
          || !Validator.length(_this.chapter.courseId, "课程ID", 1, 8)) {
          return;
        }
        _this.chapter.courseId = this.course.id;

        Loading.show();
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/chapter/save', _this.chapter).then((response)=>{
          let resp = response.data;
          Loading.hide();
          if (resp.success) {
            $("#form-modal").modal("hide");
            _this.list(1);
            Toast.success("保存成功!");
          } else {
            Toast.warning(resp.message)
          }
        })
      },
      /**
       * 删除功能
       */
      del(id) {
        let _this = this;
        Confirm.show("删除大章后不可恢复，确认删除？", function () {
          Loading.show();
          _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/chapter/delete/' + id).then((response)=>{
            let resp = response.data;
            Loading.hide();
            if (resp.success) {
              _this.list(1);
              Toast.success("删除成功！");
            }
          })
        });
      },
      toSection(chapter) {
        let _this = this;
        SessionStorage.set(SESSION_KEY_CHAPTER, chapter);
        _this.$router.push("/business/section");
      }
    }
  }
</script>