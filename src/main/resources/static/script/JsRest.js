$(document).ready(getUsers());

function getUsers() {
    $("#usersTable").empty();
    $.ajax({
        url: '/api/admin/users',
        method: 'POST',
        dataType: 'json',
        timeout: 3000,
        success: function (data) {
            console.log(data);
            $.each(data, function (i, user) {
                $("#usersTable").append($('<tr>').append(
                        $('<td>').append($('<span>')).text(user.idUser),
                        $('<td>').append($('<span>')).text(user.firstName),
                        $('<td>').append($('<span>')).text(user.lastName),
                        $('<td>').append($('<span>')).text(user.age),
                        $('<td>').append($('<span>')).text(user.login),
                        $('<td>').append($('<span>')).text(user.roleByString),
                        $('<td>').append($('<button>').text("Edit").attr({
                            "type": "button",
                            "class": "btn btn-primary edit",
                            "data-toggle": "modal",
                            "data-target": "#editModal",
                        }).data("user", user)),
                        $('<td>').append($('<button>').text("Delete").attr({
                            "type": "button",
                            "id": "delete",
                            "class": "btn btn-danger",
                            "data-toggle": "modal",
                            "data-target": "#delModal",
                        }).data("user", user))
                    )
                );
            });
        }
    });
}

//Для редактирования пользователя через модальное окно
$(document).on("click", ".edit", function () {
    let user = $(this).data('user');
    $('#id').val(user.idUser);
    $('#firstName').val(user.firstName);
    $('#lastName').val(user.lastName);
    $('#email').val(user.login);
    $('#password').val(user.password);
    $('#age').val(user.age);
    $('#role').val(user.roleByString);
});

$(document).on("click", ".editUser", function () {
    let formData = $(".formEditUser").serializeArray();
    $.ajax({
        type: 'POST',
        url: '/api/admin/updateUser',
        data: formData,
        timeout: 100,
        success: function () {
            getUsers();
        }
    });
});

$('.userAdd').click(function () {
    $('#usersTableButton').trigger('click');
    let newUser = $(".formNewUser").serializeArray();
    $.ajax({
        type: 'POST',
        url: '/api/admin/addNewUser',
        data: newUser,
        timeout: 100,
        success: function () {
            $('.formAddUser')[0].reset();
            getUsers()
        }
    })
});

$(document).on("click", "#delete", function () {
    let user = $(this).data('user');
    $('#idDel').val(user.idUser);
    $('#firstNameDel').val(user.firstName);
    $('#lastNameDel').val(user.lastName);
    $('#ageDel').val(user.age);
    $('#emailDel').val(user.login);
    $(document).on("click", "#deleteUser", function () {
        $.ajax({
            type: 'POST',
            url: '/api/admin/deleteUser',
            data: {id: $('#idDel').val()},
            timeout: 100,
            success: function () {
                getUsers();
            }
        });
    });
})