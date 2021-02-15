jQuery(document).ready(function() {
    'use strict';

    const days = $('#days').val();

    $('#desayuno').on('click', function() {
        const valor = parseFloat(days * 100);
        const actual = parseFloat($('#precioTotal').text());

        if($(this).is(':checked')) {
            $('#precioTotal').text(parseFloat(actual + valor));
            $('this').val('true');
        } else {
            $('#precioTotal').text(parseFloat(actual - valor));
            $('this').val('false');
        }
    });

    $('#cochera').on('click', function() {
        const valor = parseFloat(300);
        const actual = parseFloat($('#precioTotal').text());

        if($(this).is(':checked')) {
            $('#precioTotal').text(parseFloat(actual + valor));
            $('this').val('true');
        } else {
            $('#precioTotal').text(parseFloat(actual - valor));
            $('this').val('false');
        }
    });

    $('#cancelacion').on('click', function() {
        const valor = parseFloat(days * 400);
        const actual = parseFloat($('#precioTotal').text());

        if($(this).is(':checked')) {
            $('#precioTotal').text(parseFloat(actual + valor));
            $('this').val('true');
        } else {
            $('#precioTotal').text(parseFloat(actual - valor));
            $('this').val('false');
        }
    });
});