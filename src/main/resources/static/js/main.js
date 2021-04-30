if (!($("#nav_user").data("roles").includes('ADMIN'))) {
    $("#nav_user").addClass("active");
    loadUserInformation();
    $('#pill_user').tab('show');
} else {
    var rolesList = null;
    $(document).ready(getRolesList());
}



function getRolesList() {
    $.ajax({
        type: "GET",
        url: "/admin/rest/roles/", // адрес, на который будет отправлен запрос
        contentType: "application/json",
        dataType: 'json',
        success: function(roles) { // если запрос успешен вызываем функцию
            rolesList = roles;
            loadUsers();
        }});
}

function loadUsers() {
    // console.log(rolesList);

    $.ajax({
        type: "GET",
        url: "/admin/rest/users/", // адрес, на который будет отправлен запрос
        contentType: "application/json",
        dataType: 'json',
        success: function(users){ // если запрос успешен вызываем функцию
            let html = `<tr>
                            <th>ID</th>
                            <th>First Name</th>
                            <th>Last Name</th>
                            <th>Age</th>
                            <th>Email</th>
                            <th>Roles</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>`;
            $.each(users, function () {
               html += `<tr>
                   <td> ${this.id} </td>
                   <td> ${this.name} </td>
                   <td> ${this.lastName} </td>
                   <td> ${this.age} </td>
                   <td> ${this.email} </td>
                   <td> ${this.roles.map(role => role.name).reduce(function (str, roleName) {
                       return str + '<p>' + roleName + '</p>';
               })} </td>
                    <td>
                        <button type="button" class="btn btn-info edit_user" data-toggle="modal"  data-target='#updateForm' data-id=${this.id}>
                            Edit
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger delete_user" data-toggle="modal"  data-target='#updateForm' data-id=${this.id}>
                            Delete
                        </button>
                    </td>`;

            });
            $("#users_table").html(html);
            addOnClickListenerToButtons(rolesList);
        }
    });



}

function addOnClickListenerToButtons(rolesList) {

    $(".edit_user").on('click', function (e) {
        var id = $(this).data("id");

        createForm( "PUT", id);

    });

    $(".delete_user").on('click', function (e) {
        var id = $(this).data("id");

        createForm("DELETE", id);

    });

    $("#nav_new_user").on('click', function (e) {
        // console.log("jfsl");
        createForm("POST");

    });



    $("#nav_user").on('click', function (e) {
        loadUserInformation();
    });
}

function createForm(method, id=0) {
    let intersection =[];
    $.ajax({
        type: "GET",
        url: "/admin/rest/users/" + id, // адрес, на который будет отправлен запрос
        contentType: "application/json",
        dataType: 'json',
        success: function(user){ // если запрос успешен вызываем функцию
            // console.log(user);
            let html = `<form id="form" method=${method} action="/admin/rest/users/${user.id}">`;
            if (method !== "POST") {
                html += `<div class="modal-body">`;
            }

            $.each(user, function(field, val) {

                if (field === 'roles'){
                    if (method !== "POST"){

                        intersection = rolesList.map(roleItem => roleItem.id).filter(function (itemRole) {
                            return user.roles.map(roleItem => roleItem.id).includes(itemRole);
                        });
                    }


                    html += `<div class="form-group text-center font-weight-bold">
                                    <label for="${'input_' + field + user.id}">${field}</label>
                                    <select id="${'input_' + field + user.id}" class="form-control" name="roles" multiple size="2">
                                        ${rolesList.reduce(function (str, role) {
                                            
                        if (intersection.includes(role.id)) {

                            return str + `<option value='{"id":${role.id}, "roleName":"${role.roleName}"}' selected>${role.name}</option>`;
                        } else {

                            console.log(role);
                            return str + `<option value='{"id":${role.id}, "roleName":"${role.roleName}"}'>${role.name}</option>`;
                        }
                    }, "")}
                                    </select>
                                </div>
                                    
                                </div>
                                
                            </form>`;
                    return false; //выход из цикла
                }

                html += `<div class="form-group text-center font-weight-bold" id="${'form_group_' + field + user.id}">
                                    <label for="${'input_' + field + user.id}">${field}</label>
                                    <input id="${'input_' + field + user.id}" class="form-control" name="${field}" type="text"  
                                        value="${val}">
    
                                </div>`;

            });

            $("#updateForm .modal-content #modify_user").html(html);
            $('#input_pass' + user.id).attr({"value":"", "type":"password"});
            $('#input_age' + user.id).prop('type', 'number');
            $('#input_email' + user.id).prop('type', 'email');
            if (method === "PUT") {

                $(".modal-content #form").append(`<div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type='submit' class='btn btn-info'>Edit</button>
                </div>`);
                $('#input_id' + user.id).prop('disabled', true);


            } else if (method === "DELETE") {

                $(".modal-content #form").append(`<div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type='submit' class='btn btn-danger'>Delete</button>
                    </div>`);
                $('#form_group_pass' + user.id).remove();
                $("#updateForm input").each(function () {
                    $(this).prop('disabled', true);
                    console.log(this);

                });

                $("#input_roles" + user.id).prop('disabled', true);

            } else if (method === "POST") {
                $("#add_user_form").html(html);
                $('#input_pass' + user.id).prop( 'type', 'password');
                $('#input_age' + user.id).prop('type', 'number');
                $('#input_email' + user.id).prop('type', 'email');
                $('#form_group_id' + user.id).remove();
                $("#add_user_form #form").append("<button type='submit' class='btn btn-success'>Save user</button>");
                $("#add_user_form #form").prop('action','/admin/rest/users');
                $("#form input").each(function () {
                    $(this).prop('value', '');
                    console.log(this);

                });


            }

        }
    });
}



    $(document).on('submit',"form", (function(e) {
        e.preventDefault();

        var form = $(this);

        let method = form.attr('method');

        /*$.ajax({
            url: form.attr('action'), // ссылка куда отправляем данные
            method: method,
            // dataType: 'json',
            contentType: false,
            processData: false,
            data: formData,
            success: function(data){
                console.log(method);

                if (method === "POST") {
                    successCreate();
                } else if (method === "PUT") {
                    successEdit();
                }   else if (method === "DELETE") {
                    successDelete();
                }
                // $("#nav_users_table").addClass("active");
                //$('#nav_users_table').tab('show');
                // var someTabTriggerEl = $('#nav_users_table');
                // var tab = new bootstrap.Tab(someTabTriggerEl);
                // loadUsers();

                // tab.show();
            }
        });*/

        var inputs = $(this).find('.form-control');

        var userData = {};
        var role = {};
        var arrRoles = [];
        $.each(inputs, function (index, input){
            if ($(input).attr('name') === 'roles') {
                $.each($(input).val(), function (index, elem) {
                    role = JSON.parse("" + elem);
                    arrRoles.push(role);
                })
                userData["roles"] = arrRoles;

            } else {
                userData[$(input).attr('name')] = $(input).val();
            }

        });


        $.ajax({
            url: form.attr('action'), // ссылка куда отправляем данные
            method: method,
            contentType: 'application/json',
            data: JSON.stringify(userData),
            success: function(){
                console.log(method);

                if (method === "POST") {
                    successCreate();
                } else if (method === "PUT") {
                    successEdit();
                }   else if (method === "DELETE") {
                    successDelete();
                }
            }});

}));


function successCreate() {
    console.log('created');

    $('#nav_users_table').tab('show');
    loadUsers();

}

function successEdit() {
    $('#updateForm').modal('hide');
    loadUsers();
    console.log("edited");
}

function successDelete() {
    $('#updateForm').modal('hide');

    loadUsers();
    console.log("deleted");
}

function loadUserInformation() {
$.ajax({
type: "GET",
url: "/rest/user/", // адрес, на который будет отправлен запрос
contentType: "application/json",
dataType: 'json',
success: function(user) { // если запрос успешен вызываем функцию
    console.log(user);
    let html = `<tr>
                    <th>ID</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Age</th>
                    <th>Email</th>
                    <th>Roles</th>
                </tr>`;
    html += `<tr>
           <td> ${user.id} </td>
           <td> ${user.name} </td>
           <td> ${user.lastName} </td>
           <td> ${user.age} </td>
           <td> ${user.email} </td>
           <td> ${user.roles.map(role => role.name).reduce(function (str, roleName) {
        return str + '<p>' + roleName + '</p>';
    })} </td>`;


    $("#user_information").html(html);
}});
}
