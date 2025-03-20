$(function () {

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

    // search Btn
    $("#searchBtn").on('click', function(){
        tableList.fnDraw();
    });


})