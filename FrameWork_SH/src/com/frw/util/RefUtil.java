package com.frw.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RefUtil {
	//http://examples.javacodegeeks.com/core-java/reflection/java-reflection-example/

	/**
	 * Fetches the full qualified name of the class
	 * @author khshaik
	 * @date Feb 02 2015
	 * @param cls
	 * @return
	 */
	public static String getFullClassName(Class<?> cls){
		return cls.getName();
	}

	/**
	 * Fetches the name of the class
	 * @author khshaik
	 * @date Feb 02 2015
	 * @param cls
	 * @return
	 */
	public static String getClassName(Class<?> cls){
		return cls.getSimpleName();
	}


	/**
	 * Fetches the private methods available in the class
	 * @author khshaik
	 * @date Feb 02 2015
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Method[] getDeclaredMethodsofClass(Class<?> cls) throws Exception {

		Method[] declaredMethods = cls.getDeclaredMethods();
		System.out.println("Declared Methods are: "
				+ Arrays.toString(declaredMethods));
		for (Method dmethod : declaredMethods) {
			System.out.println("method = " + dmethod.getName());
		}

		return declaredMethods;
	}

	/** 
	 * Fetches the given private method available in the class
	 * @author khshaik
	 * @date Feb 02 2015
	 * @param cls
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static Method getDeclaredMethodofClass(Class<?> cls,String methodName) throws Exception {
		Method declaredMethod=null;
		Method[] declaredMethods = cls.getDeclaredMethods();
		System.out.println("Declared Methods are: "
				+ Arrays.toString(declaredMethods));
		for (Method dmethod : declaredMethods) {
			if(dmethod.getName().equals(methodName)){
				System.out.println("method = " + dmethod.getName());
				declaredMethod=dmethod;
				break;
			}

		}

		return declaredMethod;
	}

	/**
	 * Invoke a method without params of the class
	 * @author khshaik
	 * @date Feb 02 2015
	 * @param cls
	 * @param methodName
	 * @return
	 * @throws Throwable
	 */
	public static Object invokeDeclaredMethod(Class<?> cls,String methodName) throws Throwable{
		Object obj = cls.newInstance();

		Method method = cls.getMethod(methodName);
		System.out.println("Method "+methodName+" is invoked.. ");

		Object returnValue=method.invoke(obj);

		return returnValue;
	}

	/**
	 * Invoke a given method with params in the class
	 * @author khshaik
	 * @date Feb 02 2015
	 * @param cls
	 * @param methodName
	 * @param params
	 * @param paramData
	 * @return
	 * @throws Throwable
	 */
	@SuppressWarnings("rawtypes")
	public static Object invokeDeclaredMethod(Class<?> cls,String methodName,Class[] params,Object paramData) throws Throwable{
		Object obj = cls.newInstance();

		Method method = cls.getMethod(methodName, params);
		System.out.println("Method "+methodName+" is invoked.. ");

		Object returnValue=method.invoke(obj, paramData);

		return returnValue;
	}

	/**
	 * Fetches the list of public fields available in the class
	 * @author khshaik
	 * @date Feb 02 2015
	 * @param cls
	 * @return
	 * @throws Throwable
	 */
	public static Field[] getPublicFieldsofClass(Class<?> cls) throws Throwable{
		//	Object obj = cls.newInstance();
		Field[] fields = cls.getFields();

		System.out.println("Public Fields are: "+fields);
		/*for (Field oneField : fields) {
			// get public field name
			Field field = cls.getField(oneField.getName());
			String fieldname = field.getName();
			System.out.println("Fieldname is: " + fieldname);

			// get public field type
			Object fieldType = field.getType();
			System.out.println("Type of field " + fieldname + " is: "
					+ fieldType);

			// get public field value
			Object value = field.get(obj);
			System.out.println("Value of field " + fieldname + " is: "
					+ value);

		}*/

		return fields;

	}

	/** 
	 * Fetches the given public field in the class
	 * @author khshaik
	 * @date Feb 02 2015
	 * @param cls
	 * @param fieldName
	 * @return
	 * @throws Throwable
	 */
	public static Field getPublicFieldofClass(Class<?> cls,String fieldName) throws Throwable{
		Field publicField=null;

		Field[] fields = cls.getFields();

		System.out.println("Public Fields are: "+fields);
		for (Field oneField : fields) {
			// get public field name
			Field field = cls.getField(oneField.getName());

			if (field.getName().contains(fieldName)){
				System.out.println("Fieldname is: " + fieldName+" is located..");
				publicField=field;
				break;
			}

		}

		return publicField;

	}

	/**
	 * Fetches the value of the given public field in the class
	 * @author khshaik
	 * @date Feb 02 2015
	 * @param cls
	 * @param fieldName
	 * @return
	 * @throws Throwable
	 */
	public static Object getPublicFieldValueofClass(Class<?> cls,String fieldName) throws Throwable{
		Object value =null;

		Object obj = cls.newInstance();
		Field[] fields = cls.getFields();

		System.out.println("Public Fields are: "+fields);
		for (Field oneField : fields) {
			// get public field name
			Field field = cls.getField(oneField.getName());

			if (field.getName().equals(fieldName)){
				value = field.get(obj);
				System.out.println("Value of field " + fieldName + " is: "
						+ value);

				break;
			}

		}

		return value;

	}

	/**
	 * get the private variables of the class
	 * @author khshaik
	 * @param theClass
	 * @param initializeVariable
	 * @param newValue
	 * @return
	 * @throws Throwable
	 */

	public static List<Field> getPrivateFieldsofClass(Class<?> theClass) throws Throwable{
		List<Field> privateFields = new ArrayList<Field>();

		Field[] fields = theClass.getDeclaredFields();

		for(Field field:fields){
			if(Modifier.isPrivate(field.getModifiers())){            	
				privateFields.add(field);

			}
		}
		//   System.out.println("List of private variables are "+privateFields);
		return privateFields;
	}
	/**
	 * Fetches the given private field of the class
	 * @author khshaik
	 * @date Feb 02 2015
	 * @param theClass
	 * @param initializeVariable
	 * @return
	 * @throws Throwable
	 */
	public static Field getPrivateFieldofClass(Class<?> theClass,String initializeVariable) throws Throwable{

		Field privateField = null;

		Field[] fields = theClass.getDeclaredFields();

		for(Field field:fields){
			if(Modifier.isPrivate(field.getModifiers())){
				if(field.getName().contains(initializeVariable)){
					privateField=field;
					break;

				}
			}
		}
		// System.out.println("List of private variables are "+privateField);
		return privateField;
	}
	/**
	 * Fetches the value of given private field of the class
	 * @author khshaik
	 * @date feb 02 2015
	 * @param cls
	 * @param fieldName
	 * @return
	 * @throws Throwable
	 */
	public static Object getPrivateFieldValueofClass(Class<?> cls,String fieldName) throws Throwable{
		Object value =null;

		Object obj = cls.newInstance();
		Field[] fields = cls.getDeclaredFields();

		System.out.println("Private Fields are: "+fields);
		for (Field oneField : fields) {
			//oneField.setAccessible(true);
			// get public field name
			Field field = cls.getDeclaredField(oneField.getName());

			if (field.getName().equals(fieldName)){
				field.setAccessible(true);
				value = field.get(obj);
				System.out.println("Value of field " + fieldName + " is: "
						+ value);

				break;
			}

		}

		return value;

	}

	/**
	 * Initializes the given value to the variable in the class
	 * @author khshaik
	 * @date Jan 31 2015
	 * @param theClass
	 * @param initializeVariable
	 * @param newValue
	 * @return
	 */
	public static String initPrivateVariableofClass(Class<?> theClass,String initializeVariable,Object newValue){
		String flag="False";
		try{
			Field field= getPrivateFieldofClass(theClass, initializeVariable);
			setPrivateVariable(field, newValue);
			flag="True";
		}catch (Throwable t){
			System.out.println("Unable to initialize variable.."+initializeVariable);
		}
		return flag;

	}

	/**
	 * Sets the given value to the field
	 * @author khshaik
	 * @Jan 31 2015	
	 * @param field
	 * @param newValue
	 * @throws Exception
	 */

	static void setPrivateVariable(Field field, Object newValue) throws Exception {
		//http://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection
		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, newValue);
	}

	public static String initPublicVariableofClass(Class<?> theClass,String initializeVariable,Object newValue){
		String flag="False";
		try{
			Field field= getPublicFieldofClass(theClass, initializeVariable);
			setPublicVariable(field, newValue);
			flag="True";
		}catch (Throwable t){
			System.out.println("Unable to initialize variable.."+initializeVariable);
		}
		return flag;

	}

	static void setPublicVariable(Field field, Object newValue) throws Exception {
		//http://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection


		/*  Field modifiersField = Field.class.getDeclaredField("modifiers");
		      modifiersField.setAccessible(true);
		      modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);*/

		field.set(null, newValue);
	}

}
