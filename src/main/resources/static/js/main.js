function updateSelectedColor(radio) {
    var selectedColor = document.getElementById('selectedColor');
    var color = radio.parentNode.querySelector('span').textContent;
    selectedColor.textContent = color;

     var displayedImageTwo = document.getElementById('displayedImageTwo');
     var imageSrc = radio.parentNode.querySelector('img').getAttribute('src');
     displayedImageTwo.setAttribute('src', imageSrc);

    var productId = radio.parentNode.querySelector('p').textContent;

     var addToCartButton = document.getElementById("addToCartButton");
          console.log(addToCartButton.getAttribute("data-product-id"));

     var newProductId = productId; // Giá trị mới
     addToCartButton.setAttribute("value", newProductId);

     // Thay đổi giá trị của thuộc tính "data-product-id"
     addToCartButton.setAttribute("data-product-id", newProductId);
     console.log(addToCartButton.getAttribute("data-product-id"));

     var compare = document.getElementById('productCompare');
     compare.innerHTML = '<a href="/compare/'+productId + '" class="compare-button"> + So sánh </a> |'
  }

  document.addEventListener("DOMContentLoaded", function() {
    var tabNav = document.querySelectorAll("#product-tab .tab-nav li");
    var tabContent = document.querySelectorAll("#product-tab .tab-content .tab-pane");

    // Lặp qua tất cả các tab nav
    tabNav.forEach(function(tab, index) {
      tab.addEventListener("click", function() {
        // Xóa lớp active cho tất cả các tab nav và tab content
        tabNav.forEach(function(tab) {
          tab.classList.remove("active");
        });
        tabContent.forEach(function(content) {
          content.classList.remove("active");
        });

        // Thêm lớp active cho tab được nhấn và tab content tương ứng
        this.classList.add("active");
        tabContent[index].classList.add("active");
      });
    });
  });
