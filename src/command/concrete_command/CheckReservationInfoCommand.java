package command.concrete_command;

import controller.LoginController;
import controller.ReservationController;
import dto.ReservationForm;
import java.util.List;
import java.util.Scanner;
import command.undo_command.UndoableCommand;

/**
 * 예약 정보 확인을 위한 명령 구현체
 */
public class CheckReservationInfoCommand implements UndoableCommand{
    private LoginController loginController;
    private ReservationController reservationController;
    private Scanner scanner;

    public CheckReservationInfoCommand(LoginController loginController, ReservationController reservationController) {
        this.loginController = loginController;
        this.reservationController = reservationController;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        System.out.println();
        
        if (!loginController.isLoggedIn()) {
            // 게스트 사용자의 예약 조회
            System.out.print("예약 ID를 입력하세요: ");
            String id = scanner.nextLine();
            System.out.print("비밀번호를 입력하세요: ");
            String pw = scanner.nextLine();

            ReservationForm form = reservationController.findByReservationIdAndPassword(id, pw);
            if (form != null) {
                System.out.println("예약 상세 정보:");
                System.out.println(form);
            } else {
                System.out.println("예약 정보를 찾을 수 없습니다.");
            }
        } else {
            // 로그인된 사용자의 예약 조회
            String email = loginController.getCurrentUser().getEmail();
            List<ReservationForm> reservations = reservationController.findReservationsByEmail(email);

            if (reservations.isEmpty()) {
                System.out.println("등록된 예약이 없습니다.");
                return;
            }

            System.out.println("예약 목록:");
            for (int i = 0; i < reservations.size(); i++) {
                System.out.printf("%d. %s -> %s\n", i + 1,
                        reservations.get(i).getReservedFlights().get(0).getFlight().getDeparture(),
                        reservations.get(i).getReservedFlights().get(0).getFlight().getDestination());
            }

            System.out.print("상세 정보를 볼 예약 번호를 선택하세요: ");
            int idx = scanner.nextInt() - 1;
            scanner.nextLine(); // consume newline
            if (idx >= 0 && idx < reservations.size()) {
                System.out.println(reservations.get(idx));
            } else {
                System.out.println("잘못된 선택입니다.");
            }
        }
    }

    @Override
    public String getMenuText() {
        return "8. Check Reservation Info";
    }

    @Override
    public boolean canExecute(boolean isLoggedIn) {
        return true;
    }

    @Override
    public void undo() {
        // Undo 기능은 필요하지 않음
        System.out.println("does not support undo.");
    }
}
