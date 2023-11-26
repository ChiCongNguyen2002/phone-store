

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