define(['jquery', 'jquery/counter/waypoints', 'jquery/counter/counterup'], function () {
    return {
        startup: function () {
            $('.counter').counterUp({
                delay: 10,
                time: 1000
            });

            console.log(this._formToObject('form'));
        },

        _formToObject: function (formId) {
            var result = {};
            $('#' + formId + ' [name]').each(function () {
                result[this.name] = this.value;
            });

            return result;
        }
    };
});