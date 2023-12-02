/*function saveProduct(event) {
  event.preventDefault(); // Ngăn chặn hành vi gửi biểu mẫu mặc định

  var name = document.getElementById("nameAdd").value;
  var color = document.getElementById("color").value;
  var quantity = document.getElementById("product-quantity").value;
  var price = document.getElementById("price").value;
  var description = document.getElementById("description").value;
  var photoFile = document.getElementById("photo_file").files[0];

  // Kiểm tra các trường bắt buộc
  if (name === "" || color === "" || quantity === "" || price === "" || description === "" || !photoFile) {
    alert("Vui lòng điền đầy đủ thông tin và chọn tệp tin ảnh");
    return;
  }

  // Kiểm tra định dạng số của quantity và price
  if (isNaN(quantity) || isNaN(price)) {
    alert("Số lượng và giá phải là số");
    return;
  }

  // Kiểm tra định dạng tệp tin ảnh
  var allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;
  if (!allowedExtensions.exec(photoFile.name)) {
    alert("Chỉ cho phép tải lên tệp tin ảnh có định dạng JPG, JPEG, PNG, GIF");
    return;
  }

  // Kiểm tra kích thước tệp tin ảnh
  var maxSizeInBytes = 10 * 1024 * 1024; // 10MB
  if (photoFile.size > maxSizeInBytes) {
    alert("Kích thước tệp tin ảnh quá lớn. Vui lòng chọn một tệp tin ảnh nhỏ hơn 10MB");
    return;
  }

  // Nếu không có lỗi, gửi biểu mẫu
  document.getElementById("productForm").submit();
}*/

function slideOne() {
  var slider1 = document.getElementById("slider-1");
  var slider2 = document.getElementById("slider-2");
  var minPriceInput = document.getElementById("minPrice");
  var maxPriceInput = document.getElementById("maxPrice");

  minPriceInput.value = slider1.value;
  if (parseInt(minPriceInput.value) > parseInt(maxPriceInput.value)) {
    maxPriceInput.value = minPriceInput.value;
    slider2.value = slider1.value;
  }
}

function slideTwo() {
  var slider1 = document.getElementById("slider-1");
  var slider2 = document.getElementById("slider-2");
  var minPriceInput = document.getElementById("minPrice");
  var maxPriceInput = document.getElementById("maxPrice");

  maxPriceInput.value = slider2.value;
  if (parseInt(maxPriceInput.value) < parseInt(minPriceInput.value)) {
    minPriceInput.value = maxPriceInput.value;
    slider1.value = slider2.value;
  }
}