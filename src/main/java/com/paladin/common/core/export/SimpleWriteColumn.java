package com.paladin.common.core.export;

import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;

import com.paladin.common.core.ConstantsContainer;
import com.paladin.framework.excel.write.WriteColumn;
import com.paladin.framework.utils.reflect.Entity;
import com.paladin.framework.utils.reflect.EntityField;

public class SimpleWriteColumn extends WriteColumn {

	private String field;
	private boolean isMap = false;
	private EntityField entityField;

	private SimpleWriteColumn() {

	}

	/**
	 * 创建实例，如果不符合条件则会返回null
	 * @param field
	 * @param clazz
	 * @param cellIndex
	 * @param name
	 * @param enumType
	 * @param width
	 * @return
	 */
	public static SimpleWriteColumn newInstance(String field, Class<?> clazz, int cellIndex, String name, String enumType, Integer width, String dateFormat) {

		if (field == null || field.length() == 0 || clazz == null) {
			return null;
		}

		SimpleWriteColumn column = new SimpleWriteColumn();
		column.field = field;

		if (Map.class.isAssignableFrom(clazz)) {
			column.isMap = true;
		} else {
			Entity entity = Entity.getEntity(clazz);
			EntityField entityField = entity.getEntityField(field);
			if (entityField == null) {
				return null;
			}
			column.entityField = entityField;
		}

		if (width != null) {
			column.setWidth(width);
		}

		column.setCellIndex(cellIndex);
		column.setEnumType(enumType);
		column.setName(name);
		column.setAlignment(CellStyle.ALIGN_CENTER);
		column.setDateFormat(dateFormat);
		
		return column;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object peelData(Object data) {
		if (data != null) {
			if (isMap) {
				return ((Map) data).get(field);
			} else {
				return entityField.getValue(data);
			}
		}
		return null;
	}

	@Override
	public String getEnumName(Object value) {
		if (value == null) {
			return getDefaultEmptyValue();
		}
		return ConstantsContainer.getTypeValue(getEnumType(), value.toString());
	}
}
