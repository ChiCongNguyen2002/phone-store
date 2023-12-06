
function calculatePrice(input) {
    var row = input.closest('tr');
    console.log(row);
    var quantity = row.querySelector('input[name="productQuantity[]"]').value;
    var totalAmount = row.querySelector('input[name="productAmount[]"]').value;

    if(quantity === null || totalAmount === null) {return;}

    // Tính giá bán: tổng tiền / số lượng
    var productPrice = totalAmount / quantity;

    // Hiển thị giá bán
    row.querySelector('input[name="productPrice[]"]').value = productPrice;

    calculateTotalAmount();
}


function calculateTotalAmount() {
    var table = document.getElementById('productTable').getElementsByTagName('tbody')[0];
    var rows = table.getElementsByTagName('tr');
    var totalAmount = 0;

    for (var i = 0; i < rows.length; i++) {
        var row = rows[i];
        var totalAmountInput = row.querySelector('input[name="productAmount[]"]');
        if (totalAmountInput) {
            totalAmount += parseFloat(totalAmountInput.value) || 0;
        }
    }

    document.getElementById('totalAmount').textContent = totalAmount;
}

function addRow() {
    var table = document.getElementById('productTable').getElementsByTagName('tbody')[0];
    var newRow = table.insertRow(table.rows.length);
    var cells = [];

    // Thêm trường chọn loại sản phẩm
    cells[0] = newRow.insertCell(0);
    var typeSelect = document.createElement('select');
    typeSelect.className = 'form-control';
    typeSelect.name = 'productType[]';
    typeSelect.id = 'category'; // Đặt ID cho select
    // Thêm các option tương ứng với danh sách loại sản phẩm
    typeSelect.innerHTML = document.getElementById('category').innerHTML;
    cells[0].appendChild(typeSelect);

    // Thêm trường chọn tên sản phẩm
    cells[1] = newRow.insertCell(1);
    var nameSelect = document.createElement('select');
    nameSelect.className = 'form-control';
    nameSelect.name = 'productName[]';
    nameSelect.id = 'productName'; // Đặt ID cho select
    // Thêm các option tương ứng với danh sách tên sản phẩm
<!--    nameSelect.innerHTML = document.getElementById('productName').innerHTML;-->
    cells[1].appendChild(nameSelect);

    // Thêm trường chọn màu sắc
    cells[2] = newRow.insertCell(2);
    var colorSelect = document.createElement('select');
    colorSelect.className = 'form-control';
    colorSelect.name = 'productColor[]';
    colorSelect.id = 'productColor'; // Đặt ID cho select
    // Thêm các option tương ứng với danh sách màu sắc
<!--    colorSelect.innerHTML = document.getElementById('productColor').innerHTML;-->
    cells[2].appendChild(colorSelect);

    // Thêm trường hiển thị hình ảnh
    cells[3] = newRow.insertCell(3);
    var imageDiv = document.createElement('div');
    imageDiv.name = 'productImage[]';
    imageDiv.id = 'productImage'; // Đặt ID cho div
    cells[3].appendChild(imageDiv);

    for (var i = 4; i < 7; i++) {
        cells[i] = newRow.insertCell(i);
        var input = document.createElement('input');
        input.type = 'number';
        input.className = 'form-control';
        input.name = 'product' + (i === 6 ? 'Amount' : (i === 5 ? 'Price' : 'Quantity')) + '[]';
        input.required = true;

        // Sự kiện oninput áp dụng cho trường "Tổng Tiền" và "Số Lượng"
        if (i === 4 || i === 6) {
            input.oninput = function() {
                calculatePrice(this);
            };
        }

        if (i === 5) {
            input.setAttribute('readonly','true');
        }

        cells[i].appendChild(input);
    }

    cells[7] = newRow.insertCell(7);
    var deleteButton = document.createElement('button');
    deleteButton.type = 'button';
    deleteButton.className = 'btn btn-danger';
    deleteButton.textContent = 'Xóa';
    deleteButton.onclick = function() {
        removeRow(this);
    };
    cells[7].appendChild(deleteButton);
}


function removeRow(button) {
       var row = button.parentNode.parentNode;
       row.parentNode.removeChild(row);
   }

   <!--    $(document).ready(function () {-->
   <!--            $('#category').change(function () {-->
   <!--                var selectedCategory = $(this).val();-->
   <!--                 $('#productColor').empty();-->
   <!--                 $('#productColor').append('<option value="">...</option>');-->
   <!--                 $('#productImage').empty()-->

   <!--                // Sử dụng AJAX để gửi yêu cầu đến server và nhận danh sách-->
   <!--                $.ajax({-->
   <!--                    type: 'GET',-->
   <!--                    url: '/getProducts',-->
   <!--                    data: {categoryId: selectedCategory},-->
   <!--                    success: function (data) {-->

   <!--                        $('#productName').empty();-->
   <!--                        $('#productName').append('<option value="">...</option>');-->

   <!--                        for (var i = 0; i < data.length; i++) {-->
   <!--                            $('#productName').append('<option value="' + data[i].name + '">' + data[i].name + '</option>');-->
   <!--                        }-->
   <!--                    }-->
   <!--                });-->
   <!--            });-->
   <!--        });-->

   <!--        $(document).ready(function () {-->
   <!--            $('#productName').change(function () {-->
   <!--                var selectedProduct = $(this).val();-->
   <!--                $('#productImage').empty()-->
   <!--                // Sử dụng AJAX để gửi yêu cầu đến server và nhận danh sách-->
   <!--                $.ajax({-->
   <!--                    type: 'GET',-->
   <!--                    url: '/getProductColors',-->
   <!--                    data: {productName: selectedProduct},-->
   <!--                    success: function (data) {-->

   <!--                        $('#productColor').empty();-->
   <!--                        $('#productColor').append('<option value="">...</option>');-->
   <!--                        product = data;-->
   <!--                        for (var i = 0; i < data.length; i++) {-->
   <!--                            $('#productColor').append('<option value="' + i + '">' + data[i].color + '</option>');-->
   <!--                        }-->
   <!--                    }-->
   <!--                });-->
   <!--            });-->
   <!--        });-->

   <!--         $(document).ready(function () {-->
   <!--            $('#productColor').change(function () {-->
   <!--                var selectedProductColor = $(this).val();-->
   <!--                console.log(selectedProductColor);-->
   <!--                console.log(product[selectedProductColor]);-->
   <!--                 var imagePath = "../../img/" + product[selectedProductColor].image;-->
   <!--                $('#productImage').empty().append('<img class="product-image" src="' + imagePath + '" alt="Product Image">');-->

   <!--            });-->
   <!--         });-->

var product;

$(document).ready(function () {
       // Sự kiện change chỉ áp dụng cho phần tử có id là "category" trong dòng đang xử lý
       $(document).on('change', '[id^=category]', function () {
           var selectedCategory = $(this).val();
           var productColor = $(this).closest('tr').find('[id^=productColor]');
           var productImage = $(this).closest('tr').find('[id^=productImage]');
           productColor.empty();
           productColor.append('<option value="">...</option>');
           productImage.empty();

           // Sử dụng AJAX để gửi yêu cầu đến server và nhận danh sách
           $.ajax({
               type: 'GET',
               url: '/getProducts',
               data: { categoryId: selectedCategory },
               success: function (data) {
                   var productName = $(this).closest('tr').find('[id^=productName]');
                   productName.empty();
//                   productName.append('<option value="">...</option>');
                   for (var i = 0; i < data.length; i++) {
                       productName.append('<option value="' + data[i].name + '">' + data[i].name + '</option>');
                   }
                              var selectedProduct = data[0].name;
//                              var productColor = $(this).closest('tr').find('[id^=productColor]');
//                              var productImage = $(this).closest('tr').find('[id^=productImage]');
                              productImage.empty();

                              // Sử dụng AJAX để gửi yêu cầu đến server và nhận danh sách
                              $.ajax({
                                  type: 'GET',
                                  url: '/getProductColors',
                                  data: { productName: selectedProduct },
                                  success: function (data) {
                                      product = data;
                                      productColor.empty();
                                      console.log(data);
                                      for (var i = 0; i < data.length; i++) {
                                          productColor.append('<option value="' + data[i].id + '">' + data[i].color + '</option>');
                                      }
                                       var selectedProductColor = data[0].id;
                                       console.log(selectedProductColor);
//                                       var productImage = $(this).closest('tr').find('[id^=productImage]');
                                       console.log(productImage);
                                       var imagePath = "../../img/";
                                       $.ajax({
                                           type: 'GET',
                                           url: '/getProductImage',
                                           data: { productId: selectedProductColor },
                                           success: function (data) {
                                               console.log(data);
                                               imagePath = imagePath + data;
                                               productImage.empty().append('<img class="product-image" src="' + imagePath + '" alt="Product Image">');
                                               }
                                           });
                                  }
                              });
               }.bind(this) // bind để giữ nguyên ngữ cảnh của sự kiện
           });
       });

       // Sự kiện change chỉ áp dụng cho phần tử có id là "productName" trong dòng đang xử lý
       $(document).on('change', '[id^=productName]', function changeProduct() {
           var selectedProduct = $(this).val();
           var productColor = $(this).closest('tr').find('[id^=productColor]');
           var productImage = $(this).closest('tr').find('[id^=productImage]');
           productImage.empty();

           // Sử dụng AJAX để gửi yêu cầu đến server và nhận danh sách
           $.ajax({
               type: 'GET',
               url: '/getProductColors',
               data: { productName: selectedProduct },
               success: function (data) {
                   product = data;
                   productColor.empty();
                   console.log(data);
                   for (var i = 0; i < data.length; i++) {
                       productColor.append('<option value="' + data[i].id + '">' + data[i].color + '</option>');
                   }
                    var selectedProductColor = data[0].id;
                    var imagePath = "../../img/";
                    $.ajax({
                        type: 'GET',
                        url: '/getProductImage',
                        data: { productId: selectedProductColor },
                        success: function (data) {
                            imagePath = imagePath + data;
                            productImage.empty().append('<img class="product-image" src="' + imagePath + '" alt="Product Image">');
                        }
                    });
               }
           });
       });

       // Sự kiện change chỉ áp dụng cho phần tử có id là "productColor" trong dòng đang xử lý
       $(document).on('change', '[id^=productColor]', function () {
           var selectedProductColor = $(this).val();
           console.log(selectedProductColor);
           var productImage = $(this).closest('tr').find('[id^=productImage]');
           var imagePath = "../../img/";
            $.ajax({
               type: 'GET',
               url: '/getProductImage',
               data: { productId: selectedProductColor },
               success: function (data) {
                   console.log(data);
                   imagePath = imagePath + data;
                   productImage.empty().append('<img class="product-image" src="' + imagePath + '" alt="Product Image">');
               }
            });
       });
   });

   $(document).ready(function () {
       $("#importBillForm").submit(function (event) {
           event.preventDefault(); // Ngăn chặn biểu mẫu tự động submit

           // Lấy dữ liệu từ bảng và chuyển thành một mảng JSON
//           var products = [];
//           $("#productTable tbody tr").each(function () {
//               var product = {
//                   productName: $(this).find('[name^="productName"]').val(),
//                   productId: $(this).find('[name^="productColor"]').val(),
//                   productQuantity: $(this).find('[name^="productQuantity"]').val(),
//                   productPrice: $(this).find('[name^="productPrice"]').val(),
//                   productAmount: $(this).find('[name^="productAmount"]').val()
//               };
//               products.push(product);
//           });

           var idList = "";
           var quantityList= "";
           var productPrice= "";
            $("#productTable tbody tr").each(function () {
                var productId = $(this).find('[name^="productColor"]').val();
                idList += productId + " ";

            })

           idList = idList.trim(); // Xóa khoảng trắng ở cuối nếu có
           console.log(idList);

           // Thêm một trường ẩn để gửi mảng JSON lên server
           $('<input />').attr('type', 'hidden')
               .attr('name', 'products')
               .attr('value', JSON.stringify(products))
               .appendTo('#productForm');


//           this.submit();
       });
   });


   function saveImportBill(){
        var idList = "";
        var quantityList= "";
        var priceList= "";
        var amountList="";
        var supplier = document.getElementById('supplier').value;
        var totalAmount = document.getElementById('totalAmount').textContent;
        console.log(supplier)

            $("#productTable tbody tr").each(function () {
                var productId = $(this).find('[name^="productColor"]').val();
                idList += productId + " ";

                var productPrice = $(this).find('[name^="productPrice"]').val();
                priceList += productPrice + " ";

                var productQuantity = $(this).find('[name^="productQuantity"]').val();
                quantityList += productQuantity + " ";

                var productAmount = $(this).find('[name^="productAmount"]').val();
                amountList += productAmount + " ";
            })

           idList = idList.trim();
           priceList = priceList.trim();
           quantityList = quantityList.trim();
           amountList = amountList.trim();// Xóa khoảng trắng ở cuối nếu có

           console.log(idList);
           console.log(priceList);
           console.log(quantityList);

           // Thêm một trường ẩn để gửi mảng JSON lên server
           $('<input />').attr('type', 'hidden')
                          .attr('name', 'supplierId')
                          .attr('value', supplier)
                          .appendTo('#importBillForm-Submit');

           $('<input />').attr('type', 'hidden')
               .attr('name', 'idList')
               .attr('value', idList)
               .appendTo('#importBillForm-Submit');

           $('<input />').attr('type', 'hidden')
                          .attr('name', 'priceList')
                          .attr('value', priceList)
                          .appendTo('#importBillForm-Submit');

            $('<input />').attr('type', 'hidden')
                           .attr('name', 'quantityList')
                           .attr('value', quantityList)
                           .appendTo('#importBillForm-Submit');

            $('<input />').attr('type', 'hidden')
                           .attr('name', 'amountList')
                           .attr('value', amountList)
                           .appendTo('#importBillForm-Submit');
            $('<input />').attr('type', 'hidden')
                           .attr('name', 'totalAmount')
                           .attr('value', totalAmount)
                           .appendTo('#importBillForm-Submit');

           $("#importBillForm-Submit").submit();
   }


