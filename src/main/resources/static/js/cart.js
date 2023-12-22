var button = document.querySelector('.add-to-cart-link');
var iconElement = document.querySelector('.fa.fa-user-o.userID');
var uid = iconElement.getAttribute('data-user-id');
var discountAmount = 0;
var discountedTotalPrice = 0;
var isCouponApplied = false; // Biến đánh dấu đã áp dụng mã coupon hay chưa

function addProduct(userId, productId) {
    $.ajax({
        url: "/api/AddToCart",
        type: "POST",
        data: {
            userId: userId,
            productId: productId
        },
        success: function(response) {
            totalQuantity = 0;
            loadData();
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Đã thêm vào giỏ hàng',
                showConfirmButton: false,
                timer: 1500
            })

        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
}

$(document).ready(function() {
    $('body').on('click', '#btn-apply-coupon', function(e) {
        e.preventDefault();
        var code = $("#coupon-input-control").val();
        applyCoupon(code);
    });

    $('body').on('click', '.btn-plus', function(e) {
        e.preventDefault(); // line này để khi bấm ok (alert) thì không bị nhảy lên top website
        const id = $(this).data('id');
        const quantity = parseInt($('#txt_quantity_' + id).val()) + 1;
        updateCart(id, quantity);
    });

    $('body').on('click', '.btn-minus', function(e) {
        e.preventDefault();
        const id = $(this).data('id');
        const quantity = parseInt($('#txt_quantity_' + id).val()) - 1;

        if (quantity === 0) {
            Swal.fire({
                title: 'Bạn có chắc chắn muốn xóa sản phẩm khỏi giỏ hàng?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonText: 'Xóa',
                cancelButtonText: 'Hủy',
            }).then((result) => {
                if (result.isConfirmed) {
                    // Xử lý khi người dùng xác nhận xóa
                    updateCart(id, quantity);
                }
            });
        } else {
            updateCart(id, quantity);
        }
    });

    // chức năng xóa sản phẩm
    $('body').on('click', '.delete-button', function(e) {
        e.preventDefault();
        const id = $(this).data('product-id');
        Swal.fire({
            title: 'Bạn có chắc chắn muốn xóa sản phẩm khỏi giỏ hàng?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Xóa',
            cancelButtonText: 'Hủy',
        }).then((result) => {
            if (result.isConfirmed) {
                // Xử lý khi người dùng xác nhận xóa
                updateCart(id, 0);
            }
        });
    });

    //chức năng chuyển qua trang thanh toán và gửi coupon
    $('body').on('click', '#checkout', function(e) {
        e.preventDefault();
        var code = $("#coupon-input-control").val();
        var url = "/checkout";
        var form = $('<form action="' + url + '" method="POST"></form>');
        if (code) {
            form.append('<input type="hidden" name="coupon" value="' + code + '">');
        }
        $('body').append(form);
        form.submit();
    });
});

function applyCoupon(code) {
    if (isCouponApplied) {
        Swal.fire({
            position: 'top-end',
            icon: 'error',
            title: 'Một mã coupon chỉ được áp dụng cho 1 đơn hàng',
            showConfirmButton: false,
            timer: 1500,
        });
        return;
    }
    $.ajax({
        type: "POST",
        url: "/api/Coupon/ApplyCoupon",
        data: {
            code: code
        },
        success: function(res) {
            if (res === "-1") {
                Swal.fire({
                    position: 'top-end',
                    icon: 'error',
                    title: 'Mã coupon này đã hết lượt sử dụng',
                    showConfirmButton: false,
                    timer: 1500,
                });
                return;
            } else if (res === "-2") {
                Swal.fire({
                    position: 'top-end',
                    icon: 'error',
                    title: 'Mã coupon không tồn tại',
                    showConfirmButton: false,
                    timer: 1500,
                });
                return;
            }

            var element = document.getElementById("lbl_discount_amount");
            var element2 = document.getElementById("lbl_total_discounted");
            var result = JSON.parse(res);
            var promotion = result.promotion;
            element.innerHTML = numberWithCommas(result.totalAmountAfterReduction);
            element2.innerHTML = numberWithCommas(result.sum);
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Áp dụng coupon thành công',
                showConfirmButton: false,
                timer: 1500,
            });

            isCouponApplied = true;

            if (promotion !== 0) {
                var discountAmount = result.sum - (result.sum * ((100 - promotion) / 100));
                $('#discount_amount_row').show();
                $('#total_discounted_row').show();
                $('#lbl_discount_amount').text(numberWithCommas(discountAmount));
                $('#lbl_total_discounted').text(numberWithCommas(result.sum * ((100 - promotion) / 100)));
            } else {
                $('#discount_amount_row').hide();
                $('#total_discounted_row').hide();
            }
        },
        error: function(err) {
            console.log(err);
        }
    });
}

var iconElement = document.querySelector('.fa.fa-user-o.userID');
var uid = iconElement.getAttribute('data-user-id');

function loadData() {
    $.ajax({
        url: "/api/GetListItems",
        type: "GET",
        data: {
            userId: uid
        },
        success: function(response) {
            var cartBody = $('.cart_body');
            cartBody.empty();
            var sum = 0;
            var total = 0;
            $('#lbl_number_of_items_header').text(response.length);

            response.forEach(function(item) {
                var product = JSON.parse(item.product);
                var quantity = item.quantity;
                var itemTotalPrice = product.price * quantity;
                sum += itemTotalPrice;
                var itemHTML = `
                            <tr>
                                <td><img src="/img/${product.image}" alt="${product.name}" width="64" height="64"></td>
                                <td>${product.name}</td>
                                <td>${numberWithCommas(product.price)} <span>&#8363;</span></td>
                                <td>
                                    <div class="input-append">
                                        <button class="btn-minus" data-id="${product.id}" type="button"><i class="fa fa-minus"></i></button>
                                         <input disabled class="span1" style="text-align: center;max-width: 34px" placeholder="1" id="txt_quantity_${product.id}" value="${item.quantity}" size="16" type="text">
                                        <button class="btn-plus" data-id="${product.id}" type="button"><i class="fa fa-plus"></i></button>
                                    </div>
                                </td>
                                <td>${numberWithCommas(itemTotalPrice)} <span>&#8363;</span></td>
                                <td class="delete-button" data-user-id="1" data-product-id="${product.id}" style="width: 10%;">
                                    <button><i class="fa fa-trash"></i></button>
                                </td>
                            </tr>`;
                cartBody.append(itemHTML);
            });

            $('#lbl_total').text(numberWithCommas(sum));
        },
        error: function(error) {
            console.error(error);
        }
    });
}

window.onload = function() {
    loadData();
};

function updateCart(id, quantity) {
    $.ajax({
        type: "POST",
        url: "/api/Cart/UpdateCart",
        data: {
            id: id,
            quantity: quantity
        },
        success: function(res) {
            var resObj = JSON.parse(res);
            if (resObj === "quantity is greater than stock") {
                Swal.fire({
                    position: 'top-end',
                    icon: 'error',
                    title: 'Số lượng mua đã lớn hơn số lượng trong kho của sản phẩm',
                    showConfirmButton: false,
                    timer: 1500,
                })
            }
            loadData();

        },
        error: function(err) {
            console.log(err);
        }
    });
}

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function numberWithoutCommas(x) {
    return x.toString().replace(/,/g, "");
}