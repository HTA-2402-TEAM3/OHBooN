package com.ohboon.ohboon.util;

import java.util.Arrays;
import java.util.Objects;

public class CommonValidation {

	public static void validateNull(String... input) {

		Arrays.stream(input)
			.forEach(each -> {
				if (Objects.isNull(each)) {

					throw new IllegalStateException("null 값은 입력할 수 없습니다.");
				}
			});
	}

	public static void validateBlank(String... input) {

		Arrays.stream(input)
			.forEach(each -> {
				if (each.isBlank()) {
					throw new IllegalStateException(each + "에 빈 값은 입력할 수 없습니다.");
				}
			});
	}
}
