var ratingValue = 0;

function setRating(star) {
  ratingValue = star.getAttribute("data-value");
  document.getElementById("rating-value").value = ratingValue;

    // Lấy ngày hiện tại
    var currentDate = new Date();
    var formattedDate = currentDate.toISOString().split(".")[0];
    document.getElementById("date-review").value = formattedDate;


  var stars = document.getElementsByClassName("star");
  for (var i = 0; i < stars.length; i++) {
    if (i < ratingValue) {
      stars[i].src = "/img/star.png";
      stars[i].classList.add("selected");
    } else {
      stars[i].src = "/img/star_2.png";
      stars[i].classList.remove("selected");
    }
  }
}

function fillStars(star) {
  var stars = document.getElementsByClassName("star");
  for (var i = 0; i < stars.length; i++) {
    if (i <= star.getAttribute("data-value")) {
      stars[i].src = "/img/star.png";
    } else {
      stars[i].src = "/img/star_2.png";
    }
  }
}

function resetStars() {
  var stars = document.getElementsByClassName("star");
  for (var i = 0; i < stars.length; i++) {
    if (i < ratingValue) {
      stars[i].src = "/img/star.png";
    } else {
      stars[i].src = "/img/star_2.png";
    }
  }
}

function saveReview(event) {
  event.preventDefault(); // Ngăn chặn hành vi gửi biểu mẫu mặc định

  var message = document.getElementById("message").value;
  var date = document.getElementById("date-review").value;
  var rating = document.getElementById("rating-value").value;

if (message.trim() === "") {
  alert("Vui lòng nhập nội dung");
  return;
}

// Kiểm tra xem trường date có được nhập hay không
if (date.trim() === "") {
  alert("Vui lòng nhập ngày");
  return;
}

// Kiểm tra xem trường rating có được chọn hay không
if (rating === "") {
  alert("Vui lòng chọn đánh giá");
  return;
} else if (isNaN(rating) || rating < 1 || rating > 5) {
  alert("Đánh giá không hợp lệ");
  return;
}

  document.getElementById("reviewForm").submit();
}