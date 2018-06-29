var ajaxUrl = "ajax/admin/meals/";
var datatableApi;

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();

});

function mealsFilter() {
    var form = $("#filterForm");
        $.ajax({
            type: "POST",
            url: ajaxUrl+ "filter",
            data: form.serialize(),
             success: function (data) {
                     datatableApi.clear().rows.add(data).draw();
             }
        });

    }

    function resetFilter() {
        document.getElementById('filterForm').reset();
        mealsFilter();
    }

