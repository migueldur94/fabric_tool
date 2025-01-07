package dev.mike.tool.validation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidateGroup {

    public static void validate(Validator... valuesToValidate) {
        List<String> wrongValues = Arrays.stream(valuesToValidate).filter(Validator::isWrong).map(Validator::getMessage).collect(Collectors.toList());
        if (!wrongValues.isEmpty()) {
            throw new WrongValuesException(wrongValues);
        }
    }

}
