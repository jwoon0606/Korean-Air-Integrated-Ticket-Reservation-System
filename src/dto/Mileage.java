package dto;

public class Mileage {
    private int mileage;
    private String membershipNumber;

    public Mileage(int mileage) {
        this.mileage = mileage;
    }

    public Mileage(String string, String membershipNumber) {
		// TODO Auto-generated constructor stub
	}

	public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

	public String getMembershipNumber() {
		// TODO Auto-generated method stub
		return membershipNumber;
	}
}
