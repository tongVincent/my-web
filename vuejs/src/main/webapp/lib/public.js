(function ($) {
    
    $.fn.data2List = function (name) {
        var result = [];
        $.each(this, function () {
            var value = $(this).data(name);
            if (value) {
                result.push(value);
            }
        });
        return result;
    };

    $.fn.myAjax = function(url, options) {
        if (typeof url === "object") {
            options = url;
            url = undefined;
        }
        options = options || {};

        options.searchObject = $(this);
        $ajax(url, options);
    };

    /**
     * 对数组以分隔符进行组装，与数组的join方法不同，对与元素为空的不进行组合
     * @param arr
     * @param separate
     * @returns {string}
     */
    $.arrayToString = function(arr, separate) {
        separate = separate || ";";

        if (!arr) {
            return "";
        }

        var str = "";
        $.each(arr, function(i, obj) {
            if (obj) {
                str += (obj + separate)
            }
        });
        return str;
    };

    $.intersect = function (a, b, equal) {
        var first = $.copyArray(a);
        return $.map($.unique(first), function (val) {
            return $.isInArray(val, b, equal) ? val : null;
        });
    };

    $.copyArray = function (a) {
        var first = [];
        $.each(a, function (i, val) {
            first.push(val);
        });
        return first;
    };

    $.isInArray = function (elem, arr, i, equal) {
        if (typeof i === "function") {
            equal = i;
            i = undefined;
        }

        if (typeof equal !== "function") {
            equal = function (a, b) {
                return a === b;
            }
        }

        var len;
        if (arr) {
            len = arr.length;
            i = i ? i < 0 ? Math.max(0, len + i) : i : 0;

            for (; i < len; i++) {
                if (i in arr && equal(arr[i], elem)) {
                    return true;
                }
            }
        }

        return false;
    };

    $.getInArray = function (elem, arr, i, equal) {
        if (typeof i === "function") {
            equal = i;
            i = undefined;
        }

        if (typeof equal !== "function") {
            equal = function (a, b) {
                return a === b;
            }
        }

        var len;
        if (arr) {
            len = arr.length;
            i = i ? i < 0 ? Math.max(0, len + i) : i : 0;

            for (; i < len; i++) {
                if (i in arr && equal(arr[i], elem)) {
                    return arr[i];
                }
            }
        }
    };

    /**
     * 获取符合条件的所有元素
     * @param elem
     * @param arr
     * @param i
     * @param equal
     * @returns {*}
     */
    $.getAllInArray = function (elem, arr, i, equal) {
        if (typeof i === "function") {
            equal = i;
            i = undefined;
        }

        if (typeof equal !== "function") {
            equal = function (a, b) {
                return a === b;
            }
        }

        var len, result = [];
        if (arr) {
            len = arr.length;
            i = i ? i < 0 ? Math.max(0, len + i) : i : 0;

            for (; i < len; i++) {
                if (i in arr && equal(arr[i], elem)) {
                    result.push(arr[i]);
                }
            }
        }
        return result;
    };

    $.getIndexInArray = function (elem, arr, i, equal) {
        if (typeof i === "function") {
            equal = i;
            i = undefined;
        }

        if (typeof equal !== "function") {
            equal = function (a, b) {
                return a === b;
            }
        }

        var len;
        if (arr) {
            len = arr.length;
            i = i ? i < 0 ? Math.max(0, len + i) : i : 0;

            for (; i < len; i++) {
                if (i in arr && equal(arr[i], elem)) {
                    return i;
                }
            }
        }

        return -1;
    };

    /**
     * floor形式的舍入行为：抹去后面的小数，默认2位小数
     * @param num
     * @param digit 保留的小数位数
     */
    $.floor = function (num, digit) {
        var value = parseFloat(num);
        if (isNaN(value)) {
            return num;
        }

        digit = digit || 2;
        value = value.toFixed(digit + 1);

        return parseFloat(value.substring(0, value.lastIndexOf('.') + digit + 1));
    };

    /**
     * 四舍五入，默认2位小数，返回数值
     * @param num
     * @param digit
     */
    $.round = function (num, digit) {
        var value = parseFloat(num);
        if (isNaN(value)) {
            return num;
        }

        digit = digit || 2;
        value = value.toFixed(digit);

        return parseFloat(value);
    };

    /**
     * 对数值保留digit位，四舍五入，返回字符串
     * @param num
     * @param digit
     */
    $.fixed = function (num, digit) {
        digit = digit || 2;
        var number = parseFloat(num);
        return isNaN(number) ? num : number.toFixed(digit);
    };

    /**
     * 浮点数的是否相等
     * @param left
     * @param right
     * @param scale
     * @returns {boolean}
     */
    $.floatEqual = function (left, right, scale) {
        scale = scale == undefined ? 6: scale;
        return $.fixed(left, scale) == $.fixed(right, scale);
    };

    /**
     * 比较浮点数left < right
     * @param left
     * @param right
     * @param scale
     * @returns {boolean}
     */
    $.floatLt = function (left, right, scale) {
        scale = scale == undefined ? 6: scale;
        return !$.floatEqual(left, right, scale) && parseFloat(left) < parseFloat(right);
    };

    /**
     * 比较浮点数left <= right
     * @param left
     * @param right
     * @param scale
     * @returns {boolean}
     */
    $.floatLte = function (left, right, scale) {
        scale = scale == undefined ? 6: scale;
        return $.floatEqual(left, right, scale) || parseFloat(left) < parseFloat(right);
    };

    /**
     * 表单提交，目前只支持普通属性和数组属性
     * @param url
     * @param params
     * @param target
     */
    $.formSubmit = function (url, params, target) {
        var $form = $("<form/>").attr("action", url).attr("method", "post").hide();
        if (target) {
            $form.attr("target", target);
        }

        if (params) {
            $.each(params, function (name, value) {
                var arr = value;
                if (!$.isArray(value)) {
                    arr = [value];
                }
                $.each(arr, function (i, obj) {
                    var $input = $("<input/>").attr("name", name).attr("value", obj);
                    $form.append($input);
                });
            });
        }
        $("body").append($form);
        $form[0].submit();
    };

    /**
     * 自定义的定时器
     * @param callback 定时器执行的回调函数，当需要再次执行此回调函数，请返回true，否则，将终止定时器
     * @param delay 定时器执行的周期，单位毫秒
     */
    $.myInterval = function(callback, delay) {
        if (callback()) {
            var handle = setInterval(function() {
                var needCallAgain = callback();
                if (!needCallAgain) {
                    clearInterval(handle);
                }
            }, delay || 1000);
        }
    };

    $.myLink = function (url, target) {
        var $a = $("<a/>").attr("href", url).hide();
        if (target) {
            $a.attr("target", target);
        }

        $("body").append($a);
        $a[0].click();
    };

    $.sizeByUTF8 = function (str) {
        if (!str || !(str + "").length) {
            return 0;
        }

        var content = str + "";
        var count = 0;
        for (i = 0; i < content.length; i++) {
            count++;
            if (content.charCodeAt(i) > 0x7F) {
                count++;
            }
        }

        return count;
    };

    /**
     * 下拉分页的实现 需要如下结构
     * <div class='searchDisplayArea' data-page-no="" data-total-pages="">
     *     <div class="searchResultArea"></div>
     * </div>
     * <div class="m-w01 mtb searchingArea" style="display: none;">
     *     <div class="m-bb01 lg0 mt40">
     *         <span class="m-bg01 pdt10 fz12 c04">正在加载</span>
     *     </div>
     * </div>
     * @param options 目前可以有的参数：
     *                  url 字符串或函数 必须
     *                  type http访问类型，默认post
     *                  data 发送数据对象或函数，不用包含pageNo
     *                  dataType 返回数据类型，默认html
     *                  success 成功的回调函数
     *                  range 页面离底部还有多少的时候触发ajax访问，默认0，
     */
    $.scrollPage = function (options) {
        options = options || {};
        $(window).scroll(function() {
            var $searchDisplayArea = $(".searchDisplayArea:visible");
            if (!$searchDisplayArea.length) {
                return;
            }

            var totalPages = parseInt($searchDisplayArea.data("total-pages") || 0);
            var nextPage = parseInt($searchDisplayArea.data("page-no")) + 1;

            if(nextPage > totalPages){
                return;
            }

            var range = options.range || 0;
            var totalHeight = $(window).height() + $(window).scrollTop(); // 取得目前document已经出现的内容的总高度
            if(($(document).height()-range) > totalHeight){
                return;
            }

            var $resultArea = $searchDisplayArea.find(".searchResultArea");
            if ($resultArea.data("_searching")) {
                return;
            }

            $searchDisplayArea.data("page-no", nextPage);
            $(".searchingArea").show();

            var sendData = typeof options.data == "function" ? options.data() : options.data;
            sendData = sendData || {};
            sendData.pageNo = nextPage;

            $resultArea.myAjax({
                url: typeof options.url == "function" ? options.url() : options.url,
                type: options.type || "POST",
                data: $.mySerialize(sendData),
                dataType: options.dataType || "html",
                success: function (data) {
                    $(".searchingArea").hide();

                    if (typeof options.success == "function") {
                        options.success($resultArea, data);
                    } else {
                        $resultArea.append($(data).find(".searchResultArea").html());
                    }
                }
            });
        });
    };

    /**
     * 目前只对对象和一元数组进行序列化，并且最外层应该是对象。
     * @param data
     * @returns {*}
     */
    $.mySerialize = function(data) {
        function internal(data, prefix, result) {
            var type = $.type(data);

            $.each(data, function(key, value) {
                var name = prefix + (prefix ? "." : "") + key;
                if (type === "array") {
                    name = prefix + "[" + key + "]";
                }

                value = (value == undefined || value == null) ? "" : value;
                var vType = $.type(value);

                if (vType === "object" || vType === "array") {
                    internal(value, name, result);
                } else {
                    result.push(name + "=" + value);
                }
            });
        }

        var type = $.type(data);
        if (type !== "object" && type !== "array") {
            return data;
        }

        var result = [];
        internal(data, "", result);

        return result.join("&");
    }

})(jQuery);




/**
 * options里添加了afterLogin属性，该属性用在当需要登录的时候会弹出登陆框，然后登陆成功后需要处理的事情
 * afterLogin.reload 为true表示刷新当前页面
 * afterLogin.callback 表示登陆成功后执行的回调函数
 *
 * 还添加了errorDialog属性，用来处理错误弹窗的
 *
 * options.searchMessage 正在查询的时候提示信息
 * options.searchObject 正在查询的对象，该对象如果_no_change_searching缓存为true，则不更新_searching状态
 * options.canClickAfterSuccess 当成功后是否可以再次点击
 *
 */
function $ajax(url, options) {
    if (typeof url === "object") {
        options = url;
        url = undefined;
    }

    options = options || {};

    var $context = options.searchObject;
    if ($context && $context.length) {
        if ($context.data("_searching")) {
            displayTip(options.searchMessage || "已经点击了请耐心等待。。。");
            return;
        }
        $context.data("_searching", true);
    }

    var success = options.success;

    options.success = function (data, textStatus, jqXHR) {
        if (data.needLogin) {
            $(".dialog-box-close").click();
            var $login = $('#login-dialogBox');
            if ($login.length) {
                $login.dialogBox({
                    title: '会员登录',
                    width: 410,
                    content: data.loginPopup
                });
                $("body").data("afterLogin", options.afterLogin);
                return;
            }

            window.location.href = getBasePath() + "/login.html";
            return;
        }

        if (data.error) {
            if (options.errorDialog && typeof options.errorDialog === "function") {
                options.errorDialog(data);
                return;
            }

            displayTip(data.message || "系统异常，请联系客服或重试！");
            return;
        }

        if (success && typeof success === "function") {
            success(data, textStatus, jqXHR);
        }
    };

    var complete = options.complete;
    options.complete = function (XMLHttpRequest, textStatus) {
        if ($context && $context.length && !$context.data("_no_change_searching")) {
            $context.data("_searching", false);
        }

        if (complete && typeof complete === "function") {
            complete(XMLHttpRequest, textStatus);
        }
    };

    if (!options.error) {
        options.error = function(XMLHttpRequest, textStatus, errorThrown) {
            displayTip("系统异常，请联系客服或重试！");
        }
    }

    $.ajax(url, options);
}




//推广链接 会员ID解密
function Base64() {

    // private property
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    // public method for encoding
    this.encode = function (input) {
        var output = "";
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        var i = 0;
        input = _utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
            _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
            _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
        }
        return output;
    };

    // public method for decoding
    this.decode = function (input) {
        var output = "";
        var chr1, chr2, chr3;
        var enc1, enc2, enc3, enc4;
        var i = 0;
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");
        while (i < input.length) {
            enc1 = _keyStr.indexOf(input.charAt(i++));
            enc2 = _keyStr.indexOf(input.charAt(i++));
            enc3 = _keyStr.indexOf(input.charAt(i++));
            enc4 = _keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output = output + String.fromCharCode(chr1);
            if (enc3 != 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 != 64) {
                output = output + String.fromCharCode(chr3);
            }
        }
        output = _utf8_decode(output);
        return output;
    };

    // private method for UTF-8 encoding
    _utf8_encode = function (string) {
        string = string.replace(/\r\n/g, "\n");
        var utftext = "";
        for (var n = 0; n < string.length; n++) {
            var c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }
        return utftext;
    };

    // private method for UTF-8 decoding
    _utf8_decode = function (utftext) {
        var string = "";
        var i = 0;
        var c = c1 = c2 = 0;
        while (i < utftext.length) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    };
}


/**
 * 显示提示消息
 */
function displayTip(content, reload) {
    var $commonTip = $(".commonTip");
    $commonTip.find(".content").html(content);
    $commonTip.css("display", "table");
    setTimeout(function () {
        $commonTip.hide();
        $commonTip.find(".content").html("");
        if (reload) {
            window.location.reload(true);
        }
    }, 1200);
}

function hideMobile(mobile) {
    if (!mobile) {
        return mobile;
    }
    return mobile.replace(/(\d{3})\d{4}(\d{4})/, "$1****$2");
}

/**
 * 将字符串转utf-8
 * @param str
 * @returns {string}
 */
function toUtf8(str) {
    var out, i, len, c;
    out = "";
    len = str.length;
    for (i = 0; i < len; i++) {
        c = str.charCodeAt(i);
        if ((c >= 0x0001) && (c <= 0x007F)) {
            out += str.charAt(i);
        } else if (c > 0x07FF) {
            out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
            out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        } else {
            out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
            out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
        }
    }
    return out;
}
