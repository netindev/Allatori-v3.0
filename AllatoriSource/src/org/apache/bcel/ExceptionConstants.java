package org.apache.bcel;

public interface ExceptionConstants {

	Class THROWABLE = Throwable.class;
	Class RUNTIME_EXCEPTION = RuntimeException.class;
	Class LINKING_EXCEPTION = LinkageError.class;
	Class CLASS_CIRCULARITY_ERROR = ClassCircularityError.class;
	Class CLASS_FORMAT_ERROR = ClassFormatError.class;
	Class EXCEPTION_IN_INITIALIZER_ERROR = ExceptionInInitializerError.class;
	Class INCOMPATIBLE_CLASS_CHANGE_ERROR = IncompatibleClassChangeError.class;
	Class ABSTRACT_METHOD_ERROR = AbstractMethodError.class;
	Class ILLEGAL_ACCESS_ERROR = IllegalAccessError.class;
	Class INSTANTIATION_ERROR = InstantiationError.class;
	Class NO_SUCH_FIELD_ERROR = NoSuchFieldError.class;
	Class NO_SUCH_METHOD_ERROR = NoSuchMethodError.class;
	Class NO_CLASS_DEF_FOUND_ERROR = NoClassDefFoundError.class;
	Class UNSATISFIED_LINK_ERROR = UnsatisfiedLinkError.class;
	Class VERIFY_ERROR = VerifyError.class;
	Class NULL_POINTER_EXCEPTION = NullPointerException.class;
	Class ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION = ArrayIndexOutOfBoundsException.class;
	Class ARITHMETIC_EXCEPTION = ArithmeticException.class;
	Class NEGATIVE_ARRAY_SIZE_EXCEPTION = NegativeArraySizeException.class;
	Class CLASS_CAST_EXCEPTION = ClassCastException.class;
	Class ILLEGAL_MONITOR_STATE = IllegalMonitorStateException.class;
	Class[] EXCS_CLASS_AND_INTERFACE_RESOLUTION = new Class[] { NO_CLASS_DEF_FOUND_ERROR, CLASS_FORMAT_ERROR,
			VERIFY_ERROR, ABSTRACT_METHOD_ERROR, EXCEPTION_IN_INITIALIZER_ERROR, ILLEGAL_ACCESS_ERROR };
	Class[] EXCS_FIELD_AND_METHOD_RESOLUTION = new Class[] { NO_SUCH_FIELD_ERROR, ILLEGAL_ACCESS_ERROR,
			NO_SUCH_METHOD_ERROR };
	Class[] EXCS_INTERFACE_METHOD_RESOLUTION = new Class[0];
	Class[] EXCS_STRING_RESOLUTION = new Class[0];
	Class[] EXCS_ARRAY_EXCEPTION = new Class[] { NULL_POINTER_EXCEPTION, ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION };

}
