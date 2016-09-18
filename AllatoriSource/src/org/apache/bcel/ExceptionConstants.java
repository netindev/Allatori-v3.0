package org.apache.bcel;

public interface ExceptionConstants {

	Class<Throwable> THROWABLE = Throwable.class;
	Class<RuntimeException> RUNTIME_EXCEPTION = RuntimeException.class;
	Class<LinkageError> LINKING_EXCEPTION = LinkageError.class;
	Class<ClassCircularityError> CLASS_CIRCULARITY_ERROR = ClassCircularityError.class;
	Class<ClassFormatError> CLASS_FORMAT_ERROR = ClassFormatError.class;
	Class<ExceptionInInitializerError> EXCEPTION_IN_INITIALIZER_ERROR = ExceptionInInitializerError.class;
	Class<IncompatibleClassChangeError> INCOMPATIBLE_CLASS_CHANGE_ERROR = IncompatibleClassChangeError.class;
	Class<AbstractMethodError> ABSTRACT_METHOD_ERROR = AbstractMethodError.class;
	Class<IllegalAccessError> ILLEGAL_ACCESS_ERROR = IllegalAccessError.class;
	Class<InstantiationError> INSTANTIATION_ERROR = InstantiationError.class;
	Class<NoSuchFieldError> NO_SUCH_FIELD_ERROR = NoSuchFieldError.class;
	Class<NoSuchMethodError> NO_SUCH_METHOD_ERROR = NoSuchMethodError.class;
	Class<NoClassDefFoundError> NO_CLASS_DEF_FOUND_ERROR = NoClassDefFoundError.class;
	Class<UnsatisfiedLinkError> UNSATISFIED_LINK_ERROR = UnsatisfiedLinkError.class;
	Class<VerifyError> VERIFY_ERROR = VerifyError.class;
	Class<NullPointerException> NULL_POINTER_EXCEPTION = NullPointerException.class;
	Class<ArrayIndexOutOfBoundsException> ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION = ArrayIndexOutOfBoundsException.class;
	Class<ArithmeticException> ARITHMETIC_EXCEPTION = ArithmeticException.class;
	Class<NegativeArraySizeException> NEGATIVE_ARRAY_SIZE_EXCEPTION = NegativeArraySizeException.class;
	Class<ClassCastException> CLASS_CAST_EXCEPTION = ClassCastException.class;
	Class<IllegalMonitorStateException> ILLEGAL_MONITOR_STATE = IllegalMonitorStateException.class;
	Class<?>[] EXCS_CLASS_AND_INTERFACE_RESOLUTION = new Class[] { NO_CLASS_DEF_FOUND_ERROR, CLASS_FORMAT_ERROR,
			VERIFY_ERROR, ABSTRACT_METHOD_ERROR, EXCEPTION_IN_INITIALIZER_ERROR, ILLEGAL_ACCESS_ERROR };
	Class<?>[] EXCS_FIELD_AND_METHOD_RESOLUTION = new Class[] { NO_SUCH_FIELD_ERROR, ILLEGAL_ACCESS_ERROR,
			NO_SUCH_METHOD_ERROR };
	Class<?>[] EXCS_INTERFACE_METHOD_RESOLUTION = new Class[0];
	Class<?>[] EXCS_STRING_RESOLUTION = new Class[0];
	Class<?>[] EXCS_ARRAY_EXCEPTION = new Class[] { NULL_POINTER_EXCEPTION, ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION };

}
