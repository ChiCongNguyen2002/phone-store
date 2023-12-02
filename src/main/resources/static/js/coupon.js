$('document').ready(function (){
    $('table #editButton').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $.ajax({
            url: href,
            type: 'GET',
            success: function (coupon, status) {
                $('#idEdit').val(coupon.id);
                $('#codeEdit').val(coupon.code);
                $('#countEdit').val(coupon.count);
                $('#promotionEdit').val(coupon.promotion);
                $('#descriptionEdit').val(coupon.description);
                $('#editModal').modal();
            },
            error: function (xhr, status, error) {
                console.log(error);
            }
        });
    });
});

$('document').ready(function (){
    $('table #deleteButton').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $.get(href, function (coupon, status){
            $('#idDelete').val(coupon.id);
        });
        $('#deleteModal').modal();
    });
});

function saveCoupon(event) {
  event.preventDefault();

  var code = document.getElementById("code").value;
  var count = document.getElementById("countAdd").value;
  var promotion = document.getElementById("promotionAdd").value;
  var description = document.getElementById("descriptionAdd").value;

  if (code === "" || count === "" || promotion === "" || description === "") {
    alert("Vui lòng điền đầy đủ thông tin");
    return;
  }
   // Kiểm tra nếu số lượng không được để trống và phải là số nguyên dương
     if (count.trim() === "" || !Number.isInteger(parseInt(count)) || parseInt(count) <= 0) {
       alert("Vui lòng nhập số lượng sản phẩm hợp lệ.");
       return;
     }
  document.getElementById("couponForm").submit();
}

function saveCouponEdit(event) {
  event.preventDefault();
  var code = document.getElementById("codeEdit").value;
   var count = document.getElementById("countEdit").value;
   var promotion = document.getElementById("promotionEdit").value;
   var description = document.getElementById("descriptionEdit").value;
   if (code === "" || count === "" || promotion === "" || description === "") {
     alert("Vui lòng điền đầy đủ thông tin");
     return;
   }
   // Kiểm tra nếu số lượng không được để trống và phải là số nguyên dương
   if (count.trim() === "" || !Number.isInteger(parseInt(count)) || parseInt(count) <= 0) {
     alert("Vui lòng nhập số lượng sản phẩm hợp lệ.");
     return;
   }
  document.getElementById("editForm").submit();
}