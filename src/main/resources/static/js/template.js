var allowedFileExtensions = ["jpeg", "jpg", "png", "gif", "rar", "zip", "pdf", "xls", "txt", "xlsx", "doc", "docx"];

var agencyInSystemColumns = [
    { title: "内容", name: "selections", itemField: "itemName", required: "required", inputType: "TEMPLATE-ITEM" },
    { title: "机构", name: "agencyId", required: "required", multiple: false, inputType: "SELECT-SERVER", url: "/credit/permission/agency/lower" },
    { title: "说明", name: "explain", inputType: "TEXTAREA" },
    { title: "文件照片上传", name: "explainAttachment", inputType: "ATTACHMENT", fileName: "explainAttachmentFiles", maxFileCount: 5, allowedFileExtensions: allowedFileExtensions }
]

var agencyInternalColumns = [
    { title: "内容", name: "selections", itemField: "itemName", required: "required", inputType: "TEMPLATE-ITEM" },
    { title: "所在机构", name: "agencyId", multiple: false, inputType: "SELECT-SERVER", url: "/credit/permission/agency/lower" ,editable:false},
    { title: "项目适用对象",  name: "itemTargetType",enum: "item-target-type", inputType: "SELECT" ,editable:false},
    { title: "审核机构", name: "code", required: "required", multiple: false, inputType: "SELECT-SERVER", url: "/credit/permission/code/all" },
    { title: "说明", name: "explain", inputType: "TEXTAREA" },
    { title: "文件照片上传", name: "explainAttachment", inputType: "ATTACHMENT", fileName: "explainAttachmentFiles", maxFileCount: 5, allowedFileExtensions: allowedFileExtensions }
]

var personnelInSystemColumns = [
    { title: "内容", name: "selections", itemField: "itemName", required: "required", inputType: "TEMPLATE-ITEM" },
    {
        title: "医疗人员姓名",
        name: "personnelId",
        required: "required",
        inputType: "SELECT-TREE-SERVER",
        idField:'id',
        url: "/credit/permission/people/lower",
        selectDataFilter:function (column, data) {
            let peoples = data.people;
            let agencies = data.agency;
            if(agencies) {
                agencies.filter(function (item) {
                   item.isAgency = true;
                });
            }
            let newData;
            newData = agencies.filter(function (father) {
                let children = peoples.filter(function (child) {
                    return father.uniqueCode === child.uniqueCode
                });
                if (children.length > 0) {
                    father.children = children;
                }
                return father.uniqueCode != null;
            });
            return newData;
        },
        selectedHandler:function(column, data){
            return (data && data.data.isAgency === true)?false:true;
        }
    },
    { title: "说明", name: "explain", inputType: "TEXTAREA" },
    { title: "文件照片上传", name: "explainAttachment", inputType: "ATTACHMENT", fileName: "explainAttachmentFiles", maxFileCount: 5, allowedFileExtensions: allowedFileExtensions }
]

var personnelOutSystemColumns = [
    { title: "内容", name: "selections", itemField: "itemName", required: "required", inputType: "TEMPLATE-ITEM" },
    {
        title: "人员",
        name: "personnels",
        required: "required",
        inputType: "SUB-MODEL",
        subViewField: "personnelName",
        addSubModelBtnTitle: '添加人员',
        subTitleViewHtmml: function(data) {
            var html = "<h3 style='display: inline-block;font-size: 18px;margin: 0;line-height: 1;'>" + data.personnelName + "</h3>";
            html += '<span class="text-muted text-center">' +
                '<span style="border-right:2px solid #f4f4f4;padding-right:20px;margin-left:20px;">' + $.getConstantEnumValue("sex-type", data.personnelSex) + '</span>' +
                '<span style="border-right:2px solid #f4f4f4;padding-right:20px;margin-left:20px;">' + hideIdentification(data.personnelIdentification) + '</span>' +
                '<span style="margin-left:20px;">' + omitString(data.personnelAddress, 30) + '</span>' +
                '</span>';
            return html;
        },
        subModelOptions: {
            headBorder: false,
            hearderBox: false,
            server: false,
            layerOption: {
                height: 400,
                width: 700
            },
            columns: [
                { title: "人员姓名", name: "personnelName", inputType: "TEXT" },
                { title: "人员性别", name: "personnelSex", inputType: "RADIO", enum: "sex-type" },
                { title: "人员身份证", name: "personnelIdentification", inputType: "TEXT" },
                { title: "人员地址", name: "personnelAddress", inputType: "TEXT" }
            ]
        }
    },
    { title: "说明", name: "explain", inputType: "TEXTAREA" },
    { title: "文件照片上传", name: "explainAttachment", inputType: "ATTACHMENT", fileName: "explainAttachmentFiles", maxFileCount: 5, allowedFileExtensions: allowedFileExtensions }
]

function createItemEditor(isOrg, data, el, callback) {
    var columns = [];
    if (isOrg) {
        columns = agencyInternalColumns
    } else {
        columns = data.itemTargetType == 1 ? agencyInSystemColumns : (data.itemTargetType == 2 ? personnelInSystemColumns : personnelOutSystemColumns);
    }
    var html = generateHtml({
        id: "model",
        headBorder: false,
        hearderBox: false,
        server: false,
        cancelBtn: false,
        formButtonBar:[{
            id:  'reply_btn',
            type: 'button',
            name:  '返回',
            class: 'btn btn-default btn-block',
            order: 2
        }],
        boxHeaderClass: 'box-header no-border',
        boxStyle: '-webkit-box-shadow: none;box-shadow: none;border-radius: 0;border-bottom: 1px solid #f4f4f4;',
        columns: columns,
        editFormClass: false
    });
    $(el).html(html);
    $("#reply_btn").click(function () {
        window.location = url;
    });
    $.initComponment($(el));
    var model = new tonto.Model("model", columns, {
        pattern: "edit",
        server: false,
        submitClick: function() {
            var param = model.getFormData();
            if (typeof callback === 'function') {
                callback(param, model.formSubmitBtn);
            }
        }
    });
    model.setData(data);

    return model;
}


// 标签域构建器
var _templateItemFieldBuilder = new _FieldBuilder("TEMPLATE-ITEM", {
    getEditValue: function(column, model) {
        //return model.editBody.find("input[name='" + column.name + "']").tagsinput("items")
    },
    getFormData: function(column, data, model) {
        var vals = [];
        model.editBody.find("input[name='" + column.name + "']:checked").each(function() {
            vals.push($(this).val());
        });
        data[column.name] = vals;
    },
    fillView: function(column, data, model) {
        // VIEW页面填充值时候调用
    },
    fillEdit: function(column, data, model) {
        var div = model.editBody.find("[name='" + column.name + "']"),
            selections = data[column.name],
            item = data[column.itemField],
            isMultiple = data.isMultiple == 1;

        if (selections.length == 1 && !selections[0].selectionName) {
            var s = selections[0];
            div.append('<p class="form-control-static description">' + item + '（' + $.getConstantEnumValue('selection-grade-type', s.selectionGrade) + '）&nbsp;&nbsp;<input type="checkbox" checked="checked" name="' + column.name + '" value="' + s.id + '"></p>');
        } else {
            var inputType = isMultiple ? 'checkbox' : 'radio';
            html = '<p class="form-control-static description">' + item + '</p>';
            selections.forEach(function(s) {
                html += '<p class="form-control-static description">' + s.selectionName + '（' + $.getConstantEnumValue('selection-grade-type', s.selectionGrade) + '）&nbsp;&nbsp;<input type="' + inputType + '" name="' + column.name + '" value="' + s.id + '"></p>';
            });

            div.html(html);
        }

        model.editBody.find('input').iCheck({
            checkboxClass: 'icheckbox_square-blue', // 注意square和blue的对应关系
            radioClass: 'iradio_square-blue'
            //increaseArea: '10%' // optional
        });
    },
    generateViewFormHtml: function(column, isFirst, options) {
        var colspan = column.colspan || options.maxColspan;
        var html = '<label for="' + column.name + '" class="col-sm-' + options.labelSize + ' control-label">' + column.title + '：</label>\n';
        html += '<div name="' + column.name + '" class="col-sm-' + ((options.maxColspan - 1) * (options.inputSize + options.labelSize) + options.inputSize) + '"></div>\n';
        return {
            colspan: colspan,
            html: html
        };
    },
    generateEditFormHtml: function(column, isFirst, options) {
        var colspan = column.colspan || options.maxColspan,
            required = column.required === 'required',
            html = '<label for="' + column.name + '" class="col-sm-' +  options.labelSize + ' control-label">' + (required ? '<i class="required-label fa fa-asterisk"></i>' : '') +
            column.title + '：</label>\n';
        html += '<div name="' + column.name + '" class="col-sm-' + ((options.maxColspan - 1) * (options.inputSize + options.labelSize) + options.inputSize) + '"></div>\n';
        return {
            colspan: colspan,
            html: html
        };
    }
});