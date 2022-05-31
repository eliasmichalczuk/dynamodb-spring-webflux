package com.elias.michalczuk.dynamodbspring;

import com.elias.michalczuk.dynamodbspring.customer.domain.Customer;
import com.elias.michalczuk.dynamodbspring.product.domain.Product;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.elias.michalczuk.dynamodbspring.CustomValidator.nameValidator;
import static com.elias.michalczuk.dynamodbspring.CustomValidator.priceValidator;
import static com.elias.michalczuk.dynamodbspring.ValidationResult.*;

//@SpringBootTest
class DynamodbSpringApplicationTests {

	@Test
	public void predicateTest() {

		System.out.println(isValid.test("111"));
		System.out.println(isValid.test("11144444"));
		isValid.negate().and(isValid);

		List.of(1, 2, 2, 3).stream().dropWhile(toDrop -> toDrop == 2)
				.collect(Collectors.toList()).forEach(System.out::println);
		System.out.println(Period.between(LocalDate.now(), LocalDate.now().minusDays(2)).getDays());

		StringPredicate.isValid().test("aaa");
		isStrPhoneNumber.apply("a");
	}
	static Predicate<String> isValid = phoneNumber -> phoneNumber.length() > 7;
	static Function<String, Boolean> isStrPhoneNumber = str -> str.startsWith("+");

	public interface StringPredicate extends Predicate<String> {
		static StringPredicate isValid() {
			return a -> a.length() > 5;
		}
	}


	@Test
	public void testValidatorFunction() {
		Product product = new Product(UUID.randomUUID(), "name", 10000l);
		System.out.println(nameValidator()
				.and(priceValidator())
				.apply(product));
	}
}
