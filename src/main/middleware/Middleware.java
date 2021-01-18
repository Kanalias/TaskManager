package main.middleware;

public abstract class Middleware {

    /*Абстрактный класс цепочек ответственности */

    private Middleware next;

    /**
     * Помогает строить цепь из объектов-проверок.
     */
    public Middleware linkWith(Middleware next) {
        this.next = next;
        return next;
    }

    /**
     * Подклассы реализуют в этом методе конкретные проверки.
     */
    public abstract boolean check(String email, String password, String confirmPassword);

    /**
     * Запускает проверку в следующем объекте или завершает проверку, если мы в
     * последнем элементе цепи.
     */
    protected boolean checkNext(String login, String password, String confirmPassword) {
        if (next == null) {
            return true;
        }
        return next.check(login, password, confirmPassword);
    }
}
