$(function () {

    var tableData = {};
    var tableList = $("#namespace_list").dataTable({
        "deferRender": true,
        "processing" : true,
        "serverSide": true,
        "ajax": {
            url: base_url + "/namespace/pageList" ,
            type:"get",
            data : function ( d ) {
                var obj = {};
                obj.namespace = $('#namespace_input').val();
                obj.offset = d.start;
                obj.pageSize = d.length;
                return obj;
            }
        },
        "searching": false,
        "ordering": false,
        //"scrollX": false,
        "columns": [
            {
                "data": 'id',
                "visible" : true,
                "width":'10%'
            },
            { "data": 'namespace', "visible" : true},
            {
                "data": 'description',
                "width":'20%'
            },
            {
                "data": 'operation' ,
                "width":'15%',
                "render": function ( data, type, row ) {
                    return function(){
                        // html
                        tableData['key'+row.id] = row;
                        var html = '<p id="'+ row.id +'" >'+
                            '<button class="btn btn-warning btn-xs update" type="button">'+ I18n.system_opt_edit +'</button>  '+
                            '<button class="btn btn-danger btn-xs delete" type="button">'+ I18n.system_opt_del +'</button>  '+
                            '</p>';

                        return html;
                    };
                }
            }
        ],
        "language" : {
            "sProcessing" : I18n.dataTable_sProcessing ,
            "sLengthMenu" : I18n.dataTable_sLengthMenu ,
            "sZeroRecords" : I18n.dataTable_sZeroRecords ,
            "sInfo" : I18n.dataTable_sInfo ,
            "sInfoEmpty" : I18n.dataTable_sInfoEmpty ,
            "sInfoFiltered" : I18n.dataTable_sInfoFiltered ,
            "sInfoPostFix" : "",
            "sSearch" : I18n.dataTable_sSearch ,
            "sUrl" : "",
            "sEmptyTable" : I18n.dataTable_sEmptyTable ,
            "sLoadingRecords" : I18n.dataTable_sLoadingRecords ,
            "sInfoThousands" : ",",
            "oPaginate" : {
                "sFirst" : I18n.dataTable_sFirst ,
                "sPrevious" : I18n.dataTable_sPrevious ,
                "sNext" : I18n.dataTable_sNext ,
                "sLast" : I18n.dataTable_sLast
            },
            "oAria" : {
                "sSortAscending" : I18n.dataTable_sSortAscending ,
                "sSortDescending" : I18n.dataTable_sSortDescending
            }
        }
    })

    // 刪除 operate
    $("#namespace_list").on('click', '.delete',function() {
        var id = $(this).parent('p').attr("id");

        layer.confirm( I18n.system_ok + I18n.system_opt_del + '?', {
            icon: 3,
            title: I18n.system_tips ,
            btn: [ I18n.system_ok, I18n.system_cancel ]
        }, function(index){
            layer.close(index);

            $.ajax({
                type : 'POST',
                url : base_url + "/namespace/remove",
                data : {
                    "id" : id
                },
                dataType : "json",
                success : function(data){
                    if (data.code == 200) {
                        layer.msg( I18n.system_success );
                        tableList.fnDraw(false);
                    } else {
                        layer.msg( data.msg || I18n.system_opt_del + I18n.system_fail );
                    }
                }
            });
        });
    });

    $("#namespace_list").on('click', '.update',function() {
        console.log("click update");
        $('#editModal').modal({backdrop: false, keyboard: false}).modal('show');
        var id = $(this).parent('p').attr("id");
        var rowData = tableData['key' + id];
        console.log(rowData);
        $("#editModal .form input[name='id']").val(rowData.id);
        $("#editModal .form input[name='namespace']").val(rowData.namespace);
        $("#editModal .form textarea[name='description']").val(rowData.description);
    });

    // search Btn
    $("#searchBtn").on('click', function(){
        tableList.fnDraw();
    });

    $("#add").click(function(){

        $('#addModal').modal({backdrop: false, keyboard: false}).modal('show');
    });

    var addModalValidate = $("#addModal .form").validate({
        errorElement : 'span',
        errorClass : 'help-block',
        focusInvalid : true,
        rules : {
            namespace : {
                required : true,
                maxlength: 50
            },
            description : {
                required : false,
                maxlength: 500
            }/*,
            executorTimeout : {
                digits:true
            },
            executorFailRetryCount : {
                digits:true
            }*/
        },
        messages : {
            namespace : {
                required : 'namespace can\'t be blank'
            },
            description : {
                rangelength: 'max 500'

            }/*,
            executorTimeout : {
                digits: I18n.system_please_input + I18n.system_digits
            },
            executorFailRetryCount : {
                digits: I18n.system_please_input + I18n.system_digits
            }*/
        },
        highlight : function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success : function(label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement : function(error, element) {
            element.parent('div').append(error);
        },
        submitHandler : function(form) {
            $.post(base_url + "/namespace/add",  $("#addModal .form").serialize(), function(data, status) {
                if (data.code == "200") {
                    $('#addModal').modal('hide');
                    layer.open({
                        title: I18n.system_tips ,
                        btn: [ I18n.system_ok ],
                        content: I18n.system_add_suc ,
                        icon: '1',
                        end: function(layero, index){
                            tableList.fnDraw();
                        }
                    });
                } else {
                    layer.open({
                        title: I18n.system_tips ,
                        btn: [ I18n.system_ok ],
                        content: (data.msg || I18n.system_add_fail),
                        icon: '2'
                    });
                }
            });
        }
    });


    var editModalValidate = $("#editModal .form").validate({
        errorElement : 'span',
        errorClass : 'help-block',
        focusInvalid : true,
        rules : {
            description : {
                required : false,
                maxlength: 500
            }/*,
            executorTimeout : {
                digits:true
            },
            executorFailRetryCount : {
                digits:true
            }*/
        },
        messages : {
            namespace : {
                required : 'namespace can\'t be blank'
            },
            description : {
                rangelength: 'max 500'

            }/*,
            executorTimeout : {
                digits: I18n.system_please_input + I18n.system_digits
            },
            executorFailRetryCount : {
                digits: I18n.system_please_input + I18n.system_digits
            }*/
        },
        highlight : function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success : function(label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement : function(error, element) {
            element.parent('div').append(error);
        },
        submitHandler : function(form) {
            $.post(base_url + "/namespace/update",  $("#editModal .form").serialize(), function(data, status) {
                if (data.code == "200") {
                    $('#editModal').modal('hide');
                    layer.open({
                        title: I18n.system_tips ,
                        btn: [ I18n.system_ok ],
                        content: I18n.system_update_suc ,
                        icon: '1',
                        end: function(layero, index){
                            tableList.fnDraw();
                        }
                    });
                } else {
                    layer.open({
                        title: I18n.system_tips ,
                        btn: [ I18n.system_ok ],
                        content: (data.msg || I18n.system_add_fail),
                        icon: '2'
                    });
                }
            });
        }
    });

    $("#addModal").on('hide.bs.modal', function () {
        addModalValidate.resetForm();
        $("#addModal .form")[0].reset();
        $("#addModal .form .form-group").removeClass("has-error");
    });

    $("#editModal").on('hide.bs.modal', function () {
        editModalValidate.resetForm();
        $("#editModal .form")[0].reset();
        $("#editModal .form .form-group").removeClass("has-error");
    });


})