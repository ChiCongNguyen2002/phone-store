$('document').ready(function (){
    $('table #editButton').on('click', function (event){
        event.preventDefault();
        var href = $(this).attr('href');
        $.ajax({
            url: href,
            type: 'GET',
            success: function (account, status) {
                $('#idEdit').val(account.id);
                $('#fullname').val(account.fullname);
                $('#phone').val(account.phone);
                $('#address').val(account.address);
                $('#email').val(account.email);
                $('#editModal').modal();
            },
            error: function (xhr, status, error) {
                console.log(error);
            }
        });
    });
});

function SaveAccount(event) {
  event.preventDefault();

  var name = document.getElementById("nameAdd").value;
  var address = document.getElementById("addressAdd").value;
  var phone = document.getElementById("phoneAdd").value;
  var email = document.getElementById("emailAdd").value;
  var password = document.getElementById("password").value;

  var phoneRegex = /^\d{10}$/;
  var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  var nameRegex = /^[a-zA-Z\s]+$/;
  if (name === "" || phone === "" || address === "" || email === "" || password === "") {
    alert("Vui lòng điền đầy đủ thông tin");
    return;
  }

  if (!phoneRegex.test(phone)) {
    alert("Số điện thoại không hợp lệ Vui Lòng nhập số khác");
     return;
  }

  if(!emailRegex.test(email)) {
    alert("Định dạng email không đúng vui lòng nhập định dạng khác");
     return;
   }

  document.getElementById("accountForm").submit();
}