define(['dojo/_base/lang', 'baf/html/form', 'app/actuator/endpoints', 'jquery'], function (lang, form, endpoints) {
    var application = form.formToObject('form');

    return {
        startup: function () {
            // TODO: refresh service block
            this.refreshHealthEndpoint();
            this.refreshMetricsEndpoint();

            // refresh when clicking the nav link
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

        refreshHealthEndpoint: function () {
            $.get(application['url'] + endpoints.HEALTH, lang.hitch(this, this._healthEndpointCallback));
        },

        refreshMetricsEndpoint: function () {
            $.get(application['url'] + endpoints.METRICS, lang.hitch(this, this._metricsEndPointCallback));
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
        },

        _healthEndpointCallback: function (data, status) {
            if (status != 'success') {
                console.log('status: ' + status + ', data: ' + this._format(data));
                return;
            }

            $('.service-block.health .service-heading.web').html(application['url']);
            $('.service-block.health .status.web').html(data['status']);
            $('.service-block.health .service-heading.db').html(data['db']['database']);
            $('.service-block.health .status.db').html(data['db']['status']);
            $('.service-block.health .status.disk').html(data['diskSpace']['status']);
            $('.service-block.health .diskSpace-total').html(data['diskSpace']['total']);
            $('.service-block.health .diskSpace-free').html(data['diskSpace']['free']);
            $('.service-block.health .diskSpace-threshold').html(data['diskSpace']['threshold']);
        },

        _metricsEndPointCallback: function (data, status) {
            if (status != 'success') {
                console.log('status: ' + status + ', data: ' + this._format(data));
                return;
            }

            $('.service-block.metrics-memory .total').html(data['mem.free'] + '/' + data['mem']);
            $('.service-block.metrics-memory .heap-used').html(data['heap.used'] + '/' + data['heap']);
            $('.service-block.metrics-memory .initial-heap').html(data['heap.init']);
        }
    };
});