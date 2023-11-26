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