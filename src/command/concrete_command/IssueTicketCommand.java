package command.concrete_command;

import command.undo_command.UndoableCommand;
import controller.LoginController;
import controller.ReservationController;
import dto.*;
import ticket.*;
import user.*;

import java.util.List;
import java.util.Scanner;

public class IssueTicketCommand implements UndoableCommand {
    private final LoginController loginController;
    private final ReservationController reservationController;
    private Ticket lastIssuedTicket;

    public IssueTicketCommand(LoginController loginController, ReservationController reservationController) {
        this.loginController = loginController;
        this.reservationController = reservationController;
    }

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        User user = loginController.getCurrentUser();
        ReservationForm selectedForm = null;

        if (user == null || user instanceof GuestPassenger) {
            // 로그인되지 않았거나 Guest일 경우: 예약번호 + 비밀번호 입력
            System.out.print("Reservation ID: ");
            String id = sc.nextLine();
            System.out.print("Reservation Password: ");
            String password = sc.nextLine();
            selectedForm = reservationController.findByReservationIdAndPassword(id, password);

        } else if (user instanceof RegisteredPassenger rp) {
            // 로그인된 Registered 사용자: 이메일로 예약 내역 조회
            List<ReservationForm> myReservations = reservationController.findReservationsByEmail(rp.getEmail());

            if (myReservations.isEmpty()) {
                System.out.println("[안내] 등록된 예약 정보가 없습니다.");
                return;
            }

            for (int i = 0; i < myReservations.size(); i++) {
                System.out.println((i + 1) + ". " + myReservations.get(i));
            }
            System.out.print("발행할 예약 번호 선택: ");
            int index = Integer.parseInt(sc.nextLine()) - 1;

            if (index >= 0 && index < myReservations.size()) {
                selectedForm = myReservations.get(index);
            } else {
                System.out.println("[오류] 유효하지 않은 선택입니다.");
                return;
            }
        }

        if (selectedForm == null) {
            System.out.println("[오류] 예약 정보를 찾을 수 없습니다.");
            return;
        }

        System.out.print("출력 방식 선택 (CLI/SWING): ");
        String format = sc.nextLine().trim().toUpperCase();

        TicketBuilder builder = format.equals("SWING") ? new SwingTicketBuilder() : new CLITicketBuilder();
        TicketIssuer issuer = new TicketIssuer(builder);
        lastIssuedTicket = issuer.issue(selectedForm);

        if (format.equals("CLI")) {
            System.out.println(lastIssuedTicket);
        }
    }

    @Override
    public boolean canExecute(boolean isLoggedIn) {
        // 로그인 여부에 관계없이 항상 실행 가능
        return true;
    }

    @Override
    public String getMenuText() {
        return "10. Issue Ticket(Check-in)";
    }

    @Override
    public void undo() {
        if (lastIssuedTicket != null) {
            System.out.println("[Undo] 마지막 발행 티켓:");
            System.out.println(lastIssuedTicket);
            lastIssuedTicket = null;
        } else {
            System.out.println("[Undo] 실행 취소할 티켓이 없습니다.");
        }
    }
}
