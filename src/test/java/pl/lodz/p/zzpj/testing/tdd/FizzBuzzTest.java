package pl.lodz.p.zzpj.testing.tdd;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pl.lodz.p.zzpj.testing.junit.tdd.FizzBuzz;
import pl.lodz.p.zzpj.testing.junit.tdd.FizzBuzzException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("FizzBuzzTest")
public class FizzBuzzTest {

    private FizzBuzz sut;

    @BeforeAll
    public void initializeFizzBuzz() {
        sut = new FizzBuzz();
        System.out.println("initializeFizzBuzz");
    }

    @BeforeEach
    public void annotationBeforeEach() {
        System.out.println("Test start");
    }

    @AfterEach
    public void annotationAfterEach() {
        System.out.println("Test completed.");
    }

    @AfterAll
    public void annotationAfterAll() {
        System.out.println("Completed");
    }

    @Test
    public void FizzBizzExceptionTest()
    {
        assertThrows(FizzBuzzException.class, () -> sut.getFizzBuzzNumber(-10));
    }

    @Test
    public void getFizzBuzzNumberTest() {
        assertAll(() -> assertEquals("Fizz", sut.getFizzBuzzNumber(3)),
                  () -> assertEquals("Fizz", sut.getFizzBuzzNumber(27)),
                  () -> assertEquals("Buzz", sut.getFizzBuzzNumber(5)),
                  () -> assertEquals("Buzz", sut.getFizzBuzzNumber(25)),
                  () -> assertEquals("FizzBuzz", sut.getFizzBuzzNumber(15)),
                  () -> assertEquals("FizzBuzz", sut.getFizzBuzzNumber(30)),
                  () -> assertEquals("2", sut.getFizzBuzzNumber(2)),
                  () -> assertEquals("7", sut.getFizzBuzzNumber(7))

        );
    }

    @Test
    public void TimeoutTest()
    {
        assertTimeout(Duration.ofSeconds(3), () -> sut.getFizzBuzzNumber(3));
    }

    @Test
    public void TrueFizzTest() throws FizzBuzzException
    {
        assumeTrue(sut.getFizzBuzzNumber(9).contains("Fizz"));
    }

    @Test void FalseFizzBuzzTest() throws FizzBuzzException
    {
        assumeFalse(sut.getFizzBuzzNumber(20).contains("FizzBuzz"));
    }

    @Test
    public void assumingThatBuzz(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < 100; i++) {
            list.add(i);
        }
        for(Integer number : list)
        {
            assumingThat((number % 5 == 0 && number % 3 != 0),
                    () -> assertEquals("Buzz", sut.getFizzBuzzNumber(number)));
        }
    }

    @Nested
    public class TestWithParameters
    {
        @Tag("NewParameter")
        @ParameterizedTest
        @ValueSource(ints = {5, 10, 15, 20})
        public void ContainsBuzz(int number) throws FizzBuzzException
        {
            assumeTrue(sut.getFizzBuzzNumber(number).contains("Buzz"));
        }

        @Tag("NewParameter")
        @ParameterizedTest
        @CsvSource({"3, 10, 15", "30, 5, 225"})
        public void ContainsFizzBuzz(int first, int second, int third) throws FizzBuzzException
        {
            assumeTrue(sut.getFizzBuzzNumber(first).contains("Fizz"));
            assumeTrue(sut.getFizzBuzzNumber(second).contains("Buzz"));
            assumeTrue(sut.getFizzBuzzNumber(third).contains("FizzBuzz"));
        }

        @Tag("NewParameter")
        @ParameterizedTest
        @CsvFileSource(resources = "/somefile.csv")
        public void FirstContainFizzSecondBuzz(int first, int second) throws FizzBuzzException
        {
            assumeTrue(sut.getFizzBuzzNumber(first).contains("Fizz"));
            assumeTrue(sut.getFizzBuzzNumber(second).contains("Buzz"));
        }
    }

    @Tag("NewParameter")
    @ParameterizedTest
    @MethodSource("numbers")
    public void AllNumbersIsBuzz(int argument) throws FizzBuzzException
    {
        assertEquals("Buzz", sut.getFizzBuzzNumber(argument));
    }

    private static Stream<Integer> numbers()
    {
        return Stream.of(125, 250, 25);
    }

    @Tag("Not used")
    @Disabled
    @Test
    public void DisableFizzBuzzTest () throws FizzBuzzException
    {
        assertEquals("FizzBuzz", sut.getFizzBuzzNumber(2));
    }

    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_11, JRE.JAVA_12, JRE.JAVA_13 })
    @Test
    public void DifferentJRE() throws FizzBuzzException {
        assertEquals("Fizz", sut.getFizzBuzzNumber(3));
    }

    @EnabledOnOs(OS.WINDOWS)
    @Test
    public void WindowsOS() throws FizzBuzzException {
        assertEquals("Fizz", sut.getFizzBuzzNumber(3));
    }

}
