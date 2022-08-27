/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientmonitoring;

import java.io.Serializable;

/**
 *
 * @author tuyen
 */
public class Message implements Serializable {

    private String Time;
    private String Action;
    private String Description;

    public Message() {

    }

    public Message(
            String timeString,
            String ActString,
            String DescriptString
    ) {
        this.Time = timeString;
        this.Action = ActString;
        this.Description = DescriptString;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String timeString) {
        this.Time = timeString;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        String actionCustom = null;
        switch (action) {
            case "ENTRY_DELETE" ->
                actionCustom = "Xóa";
            case "ENTRY_CREATE" ->
                actionCustom = "Tạo mới";
            case "ENTRY_MODIFY" ->
                actionCustom = "Chỉnh sửa";
            default ->
                actionCustom = action;
        }
        this.Action = actionCustom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(
            String desc,
            String action
    ) {
        String descResponse = null;
        switch (action) {
            case "ENTRY_DELETE" ->
                descResponse = "Xóa " + desc;
            case "ENTRY_CREATE" ->
                descResponse = "Tạo mới " + desc;
            case "ENTRY_MODIFY" ->
                descResponse = "Chỉnh sửa " + desc;
            default ->
                descResponse = action + desc;
        }
        this.Description = descResponse;
    }
}
