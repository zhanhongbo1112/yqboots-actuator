define(['dojo/_base/lang', 'baf/html/form', 'app/actuator/endpoints', 'jquery'], function (lang, form, endpoints) {
    var application = form.formToObject('form');

    return {
        startup: function () {
            var _this = this;
            $('.tab ul li').on('click', function () {
                var endpoint;
                if ($(this).hasClass('info')) {
                    endpoint = endpoints.INFO;
                } else if ($(this).hasClass('configuredProperties')) {
                    endpoint = endpoints.CONFIG_PROPS;
                } else if ($(this).hasClass('dump')) {
                    endpoint = endpoints.DUMP;
                } else if ($(this).hasClass('health')) {
                    endpoint = endpoints.HEALTH;
                } else if ($(this).hasClass('mappings')) {
                    endpoint = endpoints.MAPPINGS;
                } else if ($(this).hasClass('metrics')) {
                    endpoint = endpoints.METRICS;
                } else if ($(this).hasClass('beans')) {
                    endpoint = endpoints.BEANS;
                } else if ($(this).hasClass('environments')) {
                    endpoint = endpoints.ENVIRONMENT;
                } else if ($(this).hasClass('autoConfigurations')) {
                    endpoint = endpoints.AUTO_CONFIG;
                } else if ($(this).hasClass('trace')) {
                    endpoint = endpoints.TRACE;
                }

                console.log('invoke endpoint: ' + endpoint);
                if (endpoint) {
                    _this.refresh(endpoint);
                }
            });

            $('.tab ul li.active').click();
        },

        refresh: function (endpoint) {
            $.get(application['url'] + endpoint, lang.hitch(this, this._onRefresh));
        },

        _onRefresh: function (data, status) {
            console.log('status: ' + status);
            $('.tab-content .panel-body').html(this._format(data));
        },

        _format: function (data) {
            return '<pre class="console">' + JSON.stringify(data, null, 2) + '</pre>';
        }
    };
});