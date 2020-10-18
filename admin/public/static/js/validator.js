// 前端数据校验
Validator = {
  require: function (value, text) {
    // Tool为常用判断工具类
    if (Tool.isEmpty(value)) {
      Toast.warning(text + "不能为空");
      return false;
    } else {
      return true
    }
  },

  length: function (value, text, min, max) {
    if (Tool.isEmpty(value)) {
      return true;
    }
    if (!Tool.isLength(value, min, max)) {
      Toast.warning(text + "长度" + min + "~" + max + "位");
      return false;
    } else {
      return true
    }
  }
};