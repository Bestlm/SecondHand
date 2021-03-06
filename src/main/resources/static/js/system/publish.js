
window.onload=function (ev) {
    var UnitRows;
    var DepartmentRows;
    var UserRows;


    $('#form_submit').bind('click',function () {
        $('#publish_form').form('submit',{
            url: "/product/publish",
            success:function (data) {
                alert("保存成功");
                location.href='/user//userInfo';
            }
        })


    });


    //使用ajax来异步获取Json数据  但是vue中更加推荐使用axios
$.ajax({
    url:"/ProductCategory/findType",
    async:false,
    success:function (data) {
        UnitRows=data.one;
        DepartmentRows=data.two;
        UserRows=data.three;
        console.log(UnitRows);
        console.log(DepartmentRows);
        console.log(UserRows);
    }
});



    var vm = new Vue({
        el: "#test",
        data: {
            UnitSelected: "",
            UnitList: [],
            DepartmentSelected: "",
            DepartmentList: [],
            UserSelected: "",
            UserList: []
        },
        created: function() {
            this.UnitList = UnitRows;
            console.log(this.UnitList.length);
            this.UnitSelected = this.UnitList.length > 0 ? this.UnitList[0].id : "";

            var val = this.UnitSelected;
            this.DepartmentList = DepartmentRows.filter(function(x) {
                return x.parentId == val
            });
            this.DepartmentSelected = this.DepartmentList.length > 0 ? this.DepartmentList[0].id : "";

            val = this.DepartmentSelected;
            this.UserList = UserRows.filter(function(x) {
                return x.parentId == val
            });
            this.UserSelected = this.UserList.length > 0 ? this.UserList[0].id : "";
        },
        watch: {
            UnitSelected: function(val) {
                this.DepartmentList = DepartmentRows.filter(function(x) {
                    return x.parentId == val
                });
                this.DepartmentSelected = this.DepartmentList.length > 0 ? this.DepartmentList[0].id : "";
            },
            DepartmentSelected: function(val) {
                this.UserList = UserRows.filter(function(x) {
                    return x.parentId == val
                });
                this.UserSelected = this.UserList.length > 0 ? this.UserList[0].id : "";
            }
        }
    });
};
