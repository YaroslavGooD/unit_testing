package pl.lodz.p.zzpj.testing.junit.tdd;

public class FizzBuzz {

    public String getFizzBuzzNumber(int number) throws FizzBuzzException {
        if (number < 0) throw new FizzBuzzException();
        String result = "";

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (number % 3 == 0) {
            result += "Fizz";
        }
        if (number % 5 == 0) {
            result += "Buzz";
        }
        if(number % 3 != 0 && number % 5 != 0) {
            result += String.valueOf(number);
        }

        return result;
    }
}
