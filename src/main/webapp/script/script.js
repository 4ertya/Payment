document.addEventListener('DOMContentLoaded', () => {

    const getSort = ({target}) => {
        const order = (target.dataset.order = -(target.dataset.order || -1));
        const index = [...target.parentNode.cells].indexOf(target);
        const collator = new Intl.Collator(['en', 'ru'], {numeric: true});
        const comparator = (index, order) => (a, b) => order * collator.compare(
            a.children[index].innerHTML,
            b.children[index].innerHTML
        );

        for (const tBody of target.closest('table').tBodies) {
            tBody.append(...[...tBody.rows].sort(comparator(index, order)));
        }

        for (const cell of target.parentNode.cells) {
            cell.classList.toggle('sorted', cell === target);
        }

    };

    document.querySelectorAll('.table_sort thead').forEach(tableTH => tableTH.addEventListener('click', () => getSort(event)));

});

//SEARCH

function createExpr(arr) {
    var index = 0;
    var expr = [":containsiAND('" + arr[0] + "')"];
    for (var i = 1; i < arr.length; i++) {
        if (arr[i] === 'AND') {
            expr[index] += ":containsiAND('" + arr[i + 1] + "')";
            i++;
        } else if (arr[i] === 'OR') {
            index++;
            expr[index] = ":containsiOR('" + arr[i + 1] + "')";
            i++;
        }
    }
    return expr;
}

document.addEventListener('DOMContentLoaded', () => {
    $(".searchKey").keyup(function() {
        var searchTerm = $(".searchKey").val().replace(/["']/g, "");
        var arr = searchTerm.split(/(AND|OR)/);
        var exprs = createExpr(arr);
        var searchSplit = searchTerm.replace(/AND/g, "'):containsiAND('").replace(/OR/g, "'):containsiOR('");

        $.extend($.expr[':'], {
            'containsiAND': function(element, i, match, array) {
                return (element.textContent || element.innerText || '').toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
            }
        });

        $('.table_sort tbody tr').attr('visible', 'false');
        for (var expr in exprs) {
            $(".table_sort tbody tr" + exprs[expr]).each(function(e) {
                $(this).attr('visible', 'true');
            });
        }

        var searchCount = $('.table_sort tbody tr[visible="true"]').length;

        $('.searchCount').text('найдено ' + searchCount + ' человек');
        if (searchCount == '0') {
            $('.no-result').show();
        } else {
            $('.no-result').hide();
        }
        if ($('.searchKey').val().length == 0) {
            $('.searchCount').hide();
        } else {
            $('.searchCount').show();
        }
    });
});
    // $(".search_key").keyup(function () {
    //     _this = this;
    //
    //     $.each($(".table_sort tbody tr"), function () {
    //         if ($(this).text().toLowerCase().indexOf($(_this).val().toLowerCase()) === -1) {
    //             $(this).hide();
    //         } else {
    //             $(this).show();
    //         };
    //     });
    // });

