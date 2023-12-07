function SaveRegister(event) {
  event.preventDefault();

  var name = document.getElementById("fullname").value;
  var address = document.getElementById("address").value;
  var phone = document.getElementById("phone").value;
  var email = document.getElementById("email").value;
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

   if (password.length < 6) {
     alert("Mật khẩu phải phải hơn 6 kí tự");
     return;
   }

  document.getElementById("registerForm").submit();
}


function SaveEditAccount(event) {
  event.preventDefault();

  var fullname = document.getElementById("fullname").value;
  var address = document.getElementById("address").value;
  var phone = document.getElementById("phone").value;

  var phoneRegex = /^\d{10}$/;
  var nameRegex = /^[a-zA-Z\s]+$/;
  if (fullname === "" || phone === "" || address === "") {
    alert("Vui lòng điền đầy đủ thông tin");
    return;
  }

  if (!phoneRegex.test(phone)) {
    alert("Số điện thoại không hợp lệ Vui Lòng nhập số khác");
     return;
  }

  document.getElementById("editAccountForm").submit();
}