/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ThongKeDAO;
import DTO.ThongKeDTO;
import DTO.ThongKeTheoNamDTO;
import DTO.ThongKeTheoThangDTO;
import java.util.List;

/**
 *
 * @author HP
 */
public class ThongKeBUS {
    private final ThongKeDAO TK_DAO = new ThongKeDAO();
    
    public List<ThongKeDTO> getStatistic7DaysAgo() {
        return TK_DAO.select7DaysAgo();
    }
    
    public List<ThongKeDTO> getStatisticDaysByMonthYear(int month, int year) {
        return TK_DAO.selectDaysByMonthYear(month, year);
    }
    
    public List<ThongKeTheoNamDTO> getStatisticFromYearToYear(int fromYear, int toYear) {
        return TK_DAO.selectFromYearToYear(fromYear, toYear);
    }
    
    public List<ThongKeTheoThangDTO> getStatisticMonthByYear(int year) {
        return TK_DAO.selectMounthsByYear(year);
    }
}
