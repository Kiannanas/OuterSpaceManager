package annaikanyouzoo.com.outerspacemanager.outerspacemanager.response;

import java.util.List;

import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Attack;
import annaikanyouzoo.com.outerspacemanager.outerspacemanager.models.Report;

/**
 * Created by annaikanyouzoo on 14/03/2017.
 */

public class GetReportsResponse {

    private List<Report> reports;

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
