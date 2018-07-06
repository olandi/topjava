var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({

        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },

        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date/*.replace('T',' ')*//*.substring(0, 10)*/;
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "createdRow": function (row, data, dataIndex) {

            if (!data.exceed){$(row).attr("data-mealExceed", false);}
            else {$(row).attr("data-mealExceed", true);}
        },
        "initComplete": makeEditable
    });
    makeEditable();
});