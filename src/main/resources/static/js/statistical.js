const dateFromInput = document.getElementById('dateFrom');
const dateToInput = document.getElementById('dateTo');

let savedFromDate = localStorage.getItem('savedFromDate');
let savedToDate = localStorage.getItem('savedToDate');
var chart_content = document.getElementById('chartContainer');
var divId_current = "";
var name_chart = "Doanh thu";
var chart = null;

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

var formResponse;

function create_chart(data_chart) {
    var chart = new CanvasJS.Chart("chartContainer", {
        animationEnabled: true,
        theme: "light2", // "light1", "light2", "dark1", "dark2"
        title: {
            text: name_chart
        },
        axisY: {
            title: "Doanh số (VNĐ)"
        },
        data: [{
            type: "column",
            showInLegend: false,
            legendMarkerColor: "grey",
            legendText: "",
            dataPoints: data_chart
        }]
    });
    return chart;
}

function draw_chart(chart_data) {
    var chart = create_chart(chart_data);
    chart.render();
}

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
    localStorage.setItem('savedFromDate', savedFromDate);
    localStorage.setItem('savedToDate', savedToDate);
    sessionStorage.setItem("flag", 1);
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
    //    console.log(selectOption);
    hideAllDivs(); // Ẩn tất cả các div lúc tải trang
    var flag = sessionStorage.getItem("flag");
    if (flag == 1 || flag == null) {
        if (selectOption === "top10Products") {
            showDiv("top10ProductsDiv");

        } else if (selectOption === "top5Users") {
            showDiv("top5UsersDiv");

        } else if (selectOption === "weeklyRevenue") {
            showDiv("weeklyRevenueDiv");

        } else if (selectOption === "monthlyRevenue") {
            showDiv("monthlyRevenueDiv");

        } else if (selectOption === "top5Employee") {
            showDiv("top5EmployeeDiv");

        }
        if (sessionStorage.getItem('firstLoad') === null) {
            hideAllDivs();
            sessionStorage.setItem('firstLoad', 'loaded');
        }
    } else {
        var selectOption = localStorage.getItem("selectedChartType");
        if (selectOption === "top10Products") {
            if (chart != null) {
                chart.destroy();
                draw_chart(data_chartProduct);
            } else {
                draw_chart(data_chartProduct);
            }
        } else if (selectOption === "top5Users") {
            if (chart != null) {
                chart.destroy();
                draw_chart(data_chartCustomer);
            } else {
                draw_chart(data_chartCustomer);
            }
        } else if (selectOption === "weeklyRevenue") {
            if (chart != null) {
                chart.destroy();
                draw_chart(data_chartWeekly);
            } else {
                draw_chart(data_chartWeekly);
            }
        } else if (selectOption === "monthlyRevenue") {
            if (chart != null) {
                chart.destroy();
                draw_chart(data_chart);
            } else {
                draw_chart(data_chart);
            }
        } else if (selectOption === "top5Employee") {
            if (chart != null) {
                chart.destroy();
                raw_chart(data_chartEmployee);
            } else {
                draw_chart(data_chartEmployee);
            }
        }
    }
    var savedChartType = localStorage.getItem("selectedChartType");
    if (savedChartType) {
        document.getElementById("chartType").value = savedChartType;
        displayChart();
    }
});



function displayChart() {
    var selectOption = document.getElementById("chartType").value;
    localStorage.setItem("selectedChartType", selectOption); // Lưu giá trị mới vào localStorage
    var yearSelect = document.getElementById("year");
    var yearSelectContainer = document.getElementById("yearSelectContainer");
    yearSelectContainer.style.display = "none";
    //  hideAllDivs();

    if (selectOption === "monthlyRevenue") {
        yearSelectContainer.style.display = "inline";
        yearSelect.innerHTML = ""; // Xóa tất cả các tùy chọn hiện tại
        var dateFromSelect = document.getElementById("dateFrom");
        var dateToSelect = document.getElementById("dateTo");
        var currentYear = new Date().getFullYear();
        var fromDateYear = new Date(dateFromSelect.value).getFullYear();
        var toDateYear = new Date(dateToSelect.value).getFullYear();

        for (var year = fromDateYear; year <= toDateYear; year++) {
            var option = document.createElement("option");
            option.value = year;
            option.text = year;
            yearSelect.appendChild(option);
        }
    }
}

function hideAllDivs() {
    var div1 = document.getElementById("top5UsersDiv");
    var div2 = document.getElementById("top10ProductsDiv");
    var div3 = document.getElementById("monthlyRevenueDiv");
    var div4 = document.getElementById("weeklyRevenueDiv");
    var div5 = document.getElementById("top5EmployeeDiv");

    div1.style.display = "none";
    div2.style.display = "none";
    div3.style.display = "none";
    div4.style.display = "none";
    div5.style.display = "none";
}

function showChart() {
    sessionStorage.setItem("flag", 2);
    document.getElementById("editForm").submit();
}

function showDiv(divId) {
    var div = document.getElementById(divId);
    divId_current = divId;
    div.style.display = "block";
}