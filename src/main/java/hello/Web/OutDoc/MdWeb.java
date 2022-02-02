package hello.Web.OutDoc;

import hello.Cut.Cut;
import hello.Employee.Employee;
import hello.Glue.Glue;
import hello.Operation.Operation;
import hello.Paint.Paint;

public class MdWeb {
	public MdWeb(Long mdId, Operation operation, Employee employee, int pb_quantity, Cut cut, Glue glue, Paint paint) {
		super();
		this.mdId = mdId;
		this.operation = operation;
		this.employee = employee;
		this.pb_quantity = pb_quantity;
		this.cut = cut;
		this.glue = glue;
		this.paint = paint;
	}
	public Long mdId;
	public Operation operation;
	public Employee employee;
	public int pb_quantity;
	public Cut cut;
	public Glue glue;
	public Paint paint;
}
