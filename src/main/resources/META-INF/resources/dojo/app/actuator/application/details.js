define(['dojo/_base/lang', 'baf/html/form', 'app/actuator/constants', 'jquery'], function (lang, form, constants) {
    var application = form.formToObject('form');

    // format the json string to display
    var format = function (data) {
        return '<pre class="console">' + JSON.stringify(data, null, 2) + '</pre>';
    };

    // convert disk space or memory
    var convert = function (space, unit) {
        if (!unit) {
            unit = constants.UNIT_BYTE;
        }

        var result = space ? space : 0;
        switch (unit) {
            case constants.UNIT_KB:
                result = Math.floor(result / 1024);
                break;
            case constants.UNIT_MB:
                result = Math.floor(result / (1024 * 1024));
                break;
            case constants.UNIT_GB:
                result = Math.floor(result / (1024 * 1024 * 1024));
                break;
        }

        return result + unit;
    };

    /**
     * <ul>alarm rule
     *     <li>warning-color-green (<50)</li>
     *     <li>warning-color-blue (50)</li>
     *     <li>warning-color-yellow (60)</li>
     *     <li>warning-color-orange (70)</li>
     *     <li>warning-color-red (80)</li>
     * </ul>
     */
    var alarm = function (value) {
        var result = 'warning-color-green';
        if (value >= 80) {
            result = 'warning-color-red';
        } else if (value >= 70) {
            result = 'warning-color-orange';
        } else if (value >= 60) {
            result = 'warning-color-yellow';
        } else if (value >= 50) {
            result = 'warning-color-blue';
        }

        return result;
    };

    return {
        startup: function () {
            this.refreshHealthEndpoint();
            this.refreshMetricsEndpoint();

            // refresh when clicking the nav link
            var _this = this;
            $('.tab ul li').on('click', function () {
                var endpoint;
                if ($(this).hasClass('info')) {
                    endpoint = constants.ENDPOINT_INFO;
                } else if ($(this).hasClass('configuredProperties')) {
                    endpoint = constants.ENDPOINT_CONFIG_PROPS;
                } else if ($(this).hasClass('dump')) {
                    endpoint = constants.ENDPOINT_DUMP;
                } else if ($(this).hasClass('health')) {
                    endpoint = constants.ENDPOINT_HEALTH;
                } else if ($(this).hasClass('mappings')) {
                    endpoint = constants.ENDPOINT_MAPPINGS;
                } else if ($(this).hasClass('metrics')) {
                    endpoint = constants.ENDPOINT_METRICS;
                } else if ($(this).hasClass('beans')) {
                    endpoint = constants.ENDPOINT_BEANS;
                } else if ($(this).hasClass('environments')) {
                    endpoint = constants.ENDPOINT_ENVIRONMENT;
                } else if ($(this).hasClass('autoConfigurations')) {
                    endpoint = constants.ENDPOINT_AUTO_CONFIG;
                } else if ($(this).hasClass('trace')) {
                    endpoint = constants.ENDPOINT_TRACE;
                }

                console.log('invoke endpoint: ' + endpoint);
                if (endpoint) {
                    _this.refresh(endpoint);
                }
            });

            $('.tab ul li.active').click();
        },

        refresh: function (endpoint) {
            $.get(application['url'] + endpoint, lang.hitch(this, this._refreshCallback));
        },

        _refreshCallback: function (data, status) {
            console.log('status: ' + status);
            $('.tab-content .panel-body').html(format(data));
        },

        refreshHealthEndpoint: function () {
            $.get(application['url'] + constants.ENDPOINT_HEALTH, lang.hitch(this, this._healthEndpointCallback));
        },

        _healthEndpointCallback: function (data, status) {
            if (status != 'success') {
                console.log('status: ' + status + ', data: ' + format(data));
                return;
            }

            $('.health .service-heading.web').html(application['url']);
            $('.health .status.web').html(data['status']);
            $('.health .service-heading.db').html(data['db']['database']);
            $('.health .status.db').html(data['db']['status']);
            $('.health .status.disk').html(data['diskSpace']['status']);

            // if one of services is down, show red
            var isOneDown = data['status'] != 'UP' || data['db']['status'] != 'UP' || data['diskSpace']['status'] != 'UP';
            if (isOneDown) {
                $('.health').attr('class', 'service-block health warning-color-red');
            }

            var total = data['diskSpace']['total'];
            var free = data['diskSpace']['free'];
            var used = this._statistics(Math.floor((total - free) * 100 / total));

            $('.health-disk .diskSpace-total').html(convert(total, constants.UNIT_GB));
            $('.health-disk .diskSpace-free').html(convert(free, constants.UNIT_GB));
            $('.health-disk .diskSpace-threshold').html(convert(data['diskSpace']['threshold'], constants.UNIT_MB));
            $('.health-disk .statistics .used').html(used['percentage']);
            $('.health-disk .statistics .progress-bar').attr({
                'style': used['style'],
                'aria-valuenow': used['valuenow']
            });

            // change service block to different color based on percentage
            $('.health-disk').attr('class', 'service-block health-disk ' + alarm(used['valuenow']));
        },

        refreshMetricsEndpoint: function () {
            $.get(application['url'] + constants.ENDPOINT_METRICS, lang.hitch(this, this._metricsEndPointCallback));
        },

        _metricsEndPointCallback: function (data, status) {
            if (status != 'success') {
                console.log('status: ' + status + ', data: ' + this._format(data));
                return;
            }

            var total = data['mem'];
            var free = data['mem.free'];
            var used = this._statistics(Math.floor((total - free) * 100 / total));

            var _memory = convert(free * 1024, constants.UNIT_MB) + '/' + convert(total * 1024, constants.UNIT_MB);
            $('.metrics-memory .total').html(_memory);

            var _heap = convert(data['heap.used'] * 1024, constants.UNIT_MB) + '/' + convert(data['heap'] * 1024, constants.UNIT_MB);
            $('.metrics-memory .heap-used').html(_heap);

            $('.metrics-memory .initial-heap').html(convert(data['heap.init'] * 1024, constants.UNIT_MB));

            $('.metrics-memory .statistics .used').html(used['percentage']);
            $('.metrics-memory .statistics .progress-bar').attr({
                'style': used['style'],
                'aria-valuenow': used['valuenow']
            });

            // change service block to different color based on percentage
            $('.metrics-memory').attr('class', 'service-block metrics-memory ' + alarm(used['valuenow']));
        },

        _statistics: function (value) {
            return {
                percentage: value + '%',
                style: 'width: ' + value + '%',
                valuenow: value
            };
        }
    };
});