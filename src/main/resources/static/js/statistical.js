const dateFromInput = document.getElementById('dateFrom');
const dateToInput = document.getElementById('dateTo');

let savedFromDate = localStorage.getItem('savedFromDate');
let savedToDate = localStorage.getItem('savedToDate');

// Kiểm tra xem có giá trị lưu trong localStorage hay không
if (savedFromDate === null) {
  savedFromDate = defaultFromDate;
}

if (savedToDate === null) {
  savedToDate = defaultToDate;
}

dateFromInput.value = savedFromDate;
dateToInput.value = savedToDate;

dateFromInput.addEventListener('input', function() {
  savedFromDate = dateFromInput.value;
});

dateToInput.addEventListener('input', function() {
  savedToDate = dateToInput.value;
});

function saveStatistical(event) {
  event.preventDefault();
  if (savedFromDate === '' || savedToDate === '') {
    alert('Vui lòng chọn cả hai ngày.');
    return;
  }

  if (savedFromDate > savedToDate) {
    alert('Ngày bắt đầu không được lớn hơn ngày kết thúc.');
    return;
  }

  // Save the user-modified values in localStorage
  localStorage.setItem('savedFromDate', savedFromDate);
  localStorage.setItem('savedToDate', savedToDate);

  document.getElementById("editForm").submit();
}

document.addEventListener('DOMContentLoaded', function() {
    var selectOption = localStorage.getItem("selectedChartType") || "top10Products";
    document.getElementById("chartType").value = selectOption; // Đặt giá trị mặc định cho select
    displayChart();

    var form = document.querySelector("form");
    form.addEventListener("submit", function(event) {
        event.preventDefault(); // Ngăn chặn trình duyệt load lại trang
        displayChart();
    });

    hideAllDivs(); // Ẩn tất cả các div lúc tải trang

    // Kiểm tra nếu có giá trị lưu trong localStorage, thì khôi phục giá trị của select
    var savedChartType = localStorage.getItem("selectedChartType");
    if (savedChartType) {
        document.getElementById("chartType").value = savedChartType;
        displayChart();
    }
});

function displayChart() {
    var selectOption = document.getElementById("chartType").value;
    localStorage.setItem("selectedChartType", selectOption); // Lưu giá trị mới vào localStorage

    hideAllDivs();

    if (selectOption === "top10Products") {
        showDiv("top10ProductsDiv");
    }else if (selectOption === "top5Users") {
        showDiv("top5UsersDiv");
    }else if (selectOption === "weeklyRevenue") {
        showDiv("weeklyRevenueDiv");
        // Hiển thị dữ liệu và cập nhật div "weeklyRevenueDiv"
    }else if (selectOption === "monthlyRevenue") {
        showDiv("monthlyRevenueDiv");
        // Hiển thị dữ liệu và cập nhật div "weeklyRevenueDiv"
    }
}

function hideAllDivs() {
    var div1 = document.getElementById("top5UsersDiv");
    var div2 = document.getElementById("top10ProductsDiv");
    var div3 = document.getElementById("monthlyRevenueDiv");
    var div4 = document.getElementById("weeklyRevenueDiv");
    div1.style.display = "none";
    div2.style.display = "none";
    div3.style.display = "none";
    div4.style.display = "none";
}

function showDiv(divId) {
    var div = document.getElementById(divId);
    div.style.display = "block";
}