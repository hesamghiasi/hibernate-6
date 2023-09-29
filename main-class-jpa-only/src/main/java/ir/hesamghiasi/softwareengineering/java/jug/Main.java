package ir.hesamghiasi.softwareengineering.java.jug;

import static ir.hesamghiasi.softwareengineering.java.jug.TestFindNonTransactionalMultipleEntityManager.persistAndFindNonTransactionalMultipleEntityManager;
import static ir.hesamghiasi.softwareengineering.java.jug.TestFindNonTransactionalOneEntityManager.persistAndFindNonTransactionalOneEntityManager;
import static ir.hesamghiasi.softwareengineering.java.jug.TestQueryTransactional.persistAndQueryWithTransactionalInSession;


public class Main {

    public static void main(String[] args) {
        persistAndQueryWithTransactionalInSession();
//        persistAndQueryNonTransactionalInSession();
//        persistAndFindNonTransactionalOneEntityManager();
//        persistAndFindNonTransactionalMultipleEntityManager();
    }










}