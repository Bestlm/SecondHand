$(function(){
    //计算总价
    var array = $(".qprice");
    var totalCost = 0;
    for(var i = 0;i < array.length;i++){
        var val = parseInt($(".qprice").eq(i).html().substring(1));
        totalCost += val;
    }
    $("#totalprice").html("￥"+totalCost);
    //settlement2使用
    $("#settlement2_totalCost").val(totalCost);
});

//商品++
function addQuantity(obj) {
    console.log(obj);
    let index = $(".car_btn_2").index(obj);
    let quantity = parseInt($(".car_ipt").eq(index).val());
    let stock = parseInt($(".productStock").eq(index).val());
    if (quantity == stock) {
        alert("卧槽--兄弟买超了，卖不了！");
        return false;
    }
    quantity++;
    let price = parseInt($(".productPrice").eq(index).val());
    let cost = quantity*price;
    $(".qprice").eq(index).text('￥'+cost);
    $(".car_ipt").eq(index).val(quantity);

    //11.将改变的商品操作，同步到数据库，以便我们下单进入第二步时，获得的信息是最新的
    let id = parseInt($(".id").eq(index).val());
    $.ajax({
        url:"/cart/updateCart/"+id+"/"+quantity+"/"+cost,
        type:"POST",
        dataType:"text",
        success:function (data) {
            if (data=="success"){
                //计算总价
                let array = $(".qprice");
                let totalCost = 0;
                for(let i = 0;i < array.length;i++){
                    let val = parseInt($(".qprice").eq(i).html().substring(1));
                    totalCost += val;
                }
                $("#totalprice").html("￥"+totalCost);
            }

        }
    });

}

//商品--
function subQuantity(obj) {
    console.log(obj);
    let index = $(".car_btn_1").index(obj);
    let quantity = parseInt($(".car_ipt").eq(index).val());
    if (quantity == 1) {
        alert("卧槽--你最少买一个啊！！！");
        return false;
    }
    quantity--;
    let price = parseInt($(".productPrice").eq(index).val());
    let cost = quantity*price;
    //11.将改变的商品操作，同步到数据库，以便我们下单进入第二步时，获得的信息是最新的
    let id = parseInt($(".id").eq(index).val());
    $.ajax({
        url:"/cart/updateCart/"+id+"/"+quantity+"/"+cost,
        type:"POST",
        dataType:"text",
        success:function (data) {
            if (data=="success"){
                $(".qprice").eq(index).text('￥'+cost);
                $(".car_ipt").eq(index).val(quantity);
                //计算总价
                let array = $(".qprice");
                let totalCost = 0;
                for(let i = 0;i < array.length;i++){
                    let val = parseInt($(".qprice").eq(i).html().substring(1));
                    totalCost += val;
                }
                $("#totalprice").html("￥"+totalCost);
            }

        }
    });



}


//移出购物车
function removeCart(obj){
    let index = $(".delete").index(obj);
    let id = parseInt($(".id").eq(index).val());
    if(confirm("卧槽--你不买了？")){
        window.location.href = "/cart/deleteById/"+id;
    }
}
// //商品数量++
// function addQuantity(obj){
//     var index = $(".car_btn_2").index(obj);
//     var stock = parseInt($(".productStock").eq(index).val());
//     var price = parseInt($(".productPrice").eq(index).val());
//     var inputObj = $(".car_ipt").eq(index);
//     var quantity = inputObj.val();
//     var id = $(".id").eq(index).val();
//     ++quantity;
//     if(quantity > stock){
//         quantity = stock;
//         alert("库存不足！");
//     }
//     var cost = quantity*price;
//     $.ajax({
//         url:"/product/updateCart/"+id+"/"+quantity+"/"+cost,
//         type:"POST",
//         dataType:"text",
//         success:function (data) {
//             if(data == "success"){
//                 //更新toSettlement的数据
//                 $(".qprice").eq(index).html("￥"+cost);
//                 inputObj.val(quantity);
//                 if(quantity < stock){
//                     var totalCost = parseInt($("#totalprice").html().substring(1));
//                     totalCost += price;
//                     $("#totalprice").html("￥"+totalCost);
//                 }
//                 //更新searchBar的数据
//                 $(".quantity").eq(index).text(quantity);
//                 $(".cost").eq(index).text(cost);
//
//                 var array = $(".cost");
//                 var totalCost = 0;
//                 for(var i = 0;i < array.length;i++){
//                     var val = parseInt($(".cost").eq(i).html());
//                     totalCost += val;
//                 }
//                 $("#totalCost").html("￥"+totalCost);
//             }
//         }
//     });
// }

//商品数量--
// function subQuantity(obj){
//     var index = $(".car_btn_1").index(obj);
//     var price = parseInt($(".productPrice").eq(index).val());
//     var inputObj = $(".car_ipt").eq(index);
//     var quantity = inputObj.val();
//     var id = $(".id").eq(index).val();
//     --quantity;
//     if(quantity == 0){
//         quantity = 1;
//     }
//     var cost = quantity*price;
//     $.ajax({
//         url:"/product/updateCart/"+id+"/"+quantity+"/"+cost,
//         type:"POST",
//         dataType:"text",
//         success:function(data){
//             if(data == "success"){
//                 $(".qprice").eq(index).html("￥"+cost);
//                 inputObj.val(quantity);
//                 if(quantity!=1){
//                     var totalCost = parseInt($("#totalprice").html().substring(1));
//                     totalCost -= price;
//                     $("#totalprice").html("￥"+totalCost);
//                 }
//                 $(".quantity").eq(index).text(quantity);
//                 $(".cost").eq(index).text(cost);
//
//                 var array = $(".cost");
//                 var totalCost = 0;
//                 for(var i = 0;i < array.length;i++){
//                     var val = parseInt($(".cost").eq(i).html());
//                     totalCost += val;
//                 }
//                 $("#totalCost").html("￥"+totalCost);
//             }
//         }
//     });
// }

//移出购物车
// function removeCart(obj){
//     var index = $(".delete").index(obj);
//     var id = $(".id").eq(index).val();
//     if(confirm("是否确定要删除？")){
//         window.location.href = "/product/removeCart/"+id;
//     }
// }
function settlement2() {
    var total = $("#totalprice").text();
    if (total=="￥0"){
        alert("你都没买东西，你算啥账，装杯！！");
    }else{
        window.location.href="/cart/settlement2";
    }
}
