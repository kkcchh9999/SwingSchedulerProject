import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;

import java.awt.Font;



public class RightSettingUI extends JPanel {
	//모든 일정 정보를 들고있는 필드 
	private ArrayList<Schedule> allSchedule;
	
	//
	private LeftTableUI leftUI;
	private ArrayList<DayTimeUI> timeLines;
	//알림설정 관련해서 추가한 변수,KCH
	private Alarm alarm;
	private JPanel dayTimeUIPanel;
	public RightSettingUI(ArrayList<Schedule> allSchedule, LeftTableUI leftUI, Alarm alarm) {
		this.allSchedule = allSchedule;
		this.leftUI = leftUI;
		
		createSettingUI();
	}
	
	//강의일정과 기타일정 체크박스, 알림 스위치, DayTimeUI생성
	//기본적으로 강의 일정이 체크된 상태로 변경
	//selectLecture 호출
	public void createSettingUI() {
		//버튼 클릭시 안에있는 텍스트에 밑줄 생기는것을 없앤다. 
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
		defaults.put("RadioButton.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
		defaults.put("CheckBox.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

		setBorder(new LineBorder(UIManager.getColor ("Button.light"), 2, true));
		this.setSize(390, 520);
		this.setVisible(true);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {30, 30, 30, 30, 30, 30, 30, 30, 5};
		gridBagLayout.rowHeights = new int[] {30, 30, 30, 30, 30,30, 30, 30, 30, 30,30, 30, 30, 30, 30, 5,30};
		
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		
		
		JButton btnNewButton = new JButton("일정추가");
		btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		btnNewButton.setBorder(new RoundedBorder(5));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addScheduleBtnClick();
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 3;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 5;
		gbc_btnNewButton.gridy = 1;
		add(btnNewButton, gbc_btnNewButton);
		
		ButtonGroup group = new ButtonGroup();
		
		JRadioButton chckbxNewCheckBox = new JRadioButton("강의일정");
		chckbxNewCheckBox.setBackground(UIManager.getColor ( "Panel.background" ));
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox.gridwidth = 4;
		gbc_chckbxNewCheckBox.gridx = 1;
		gbc_chckbxNewCheckBox.gridy = 1;
		add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
		group.add(chckbxNewCheckBox);
		chckbxNewCheckBox.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	selectLecture();
	        	revalidate();
	        	repaint();
	        }
	    });
		
		JRadioButton chckbxNewCheckBox_1 = new JRadioButton("기타일정");
		chckbxNewCheckBox_1.setBackground(UIManager.getColor ( "Panel.background" ));
		GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxNewCheckBox_1.gridwidth = 4;
		gbc_chckbxNewCheckBox_1.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_1.gridx = 1;
		gbc_chckbxNewCheckBox_1.gridy = 2;
		add(chckbxNewCheckBox_1, gbc_chckbxNewCheckBox_1);
		group.add(chckbxNewCheckBox_1);

    	
		chckbxNewCheckBox_1.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	selectOtherSchedule();
	        	revalidate();
	        	repaint();
	        }
	    });
		
		

		JLabel lineField = new JLabel("");
		lineField.setBorder(new MatteBorder(0, 0, 1, 0, UIManager.getColor ("Button.light")));
		GridBagConstraints gbc_lineField = new GridBagConstraints();
		gbc_lineField.insets = new Insets(0, 0, 5, 5);
		gbc_lineField.gridwidth = 10;
		gbc_lineField.fill = GridBagConstraints.HORIZONTAL;
		gbc_lineField.gridx = 0;
		gbc_lineField.gridy = 15;
		add(lineField, gbc_lineField);
		
		JCheckBox chckbxNewCheckBox_2 = new JCheckBox("알람설정");
		chckbxNewCheckBox_2.setBackground(UIManager.getColor ( "Panel.background" ));
		chckbxNewCheckBox_2.setFont(new Font("굴림", Font.PLAIN, 12));
		GridBagConstraints gbc_chckbxNewCheckBox_2 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_2.gridwidth = 3;
		gbc_chckbxNewCheckBox_2.insets = new Insets(0, 0, 0, 5);
		gbc_chckbxNewCheckBox_2.gridx = 5;
		gbc_chckbxNewCheckBox_2.gridy = 16;
		add(chckbxNewCheckBox_2, gbc_chckbxNewCheckBox_2);
		
		selectLecture();
	}
	
	//요일 및 시간을 입력받는 UI를 초기화 하는 함수
	//selectLecture와 selectOtherSchedule 함수에서 쓰인다. 
	public void dayTimeUIContainerInit(JPanel dayTimeUIContainerPanel) {
		dayTimeUIContainerPanel.setBackground(UIManager.getColor ( "Panel.background" ));
		dayTimeUIContainerPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Panel.background")));
		
		//Y축으로 추가되도록 설정
		dayTimeUIContainerPanel.setLayout(new BoxLayout(dayTimeUIContainerPanel, BoxLayout.Y_AXIS));
		
		//DayAndTime UI Panel
		dayTimeUIPanel = new JPanel();
		dayTimeUIPanel.setBackground(UIManager.getColor ( "Panel.background" ));
		dayTimeUIPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Panel.background")));
		
		//Y축으로 추가되도록 설정
		dayTimeUIPanel.setLayout(new BoxLayout(dayTimeUIPanel, BoxLayout.Y_AXIS));
		
		//기본적으로 요일 및 시간을 입력받을 수 있는 UI한줄 추가
		DayTimeUI newDayTimeUI = new DayTimeUI();
		timeLines.add(newDayTimeUI);
		dayTimeUIPanel.add(newDayTimeUI);
		
		//DayAndTime UI Container에 DayAndTime UI Panel추가
		dayTimeUIContainerPanel.add(dayTimeUIPanel);
		
		//+버튼 추가 
		JButton dayTimePlusBtn = new JButton("+");
		
		//+버튼 action추가 
		dayTimePlusBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addTimeLineBtnClick();
			}
		});
		
		//+버튼 추가 
		dayTimePlusBtn.setBackground(UIManager.getColor ( "Panel.background" ));
		dayTimePlusBtn.setBorder(null);
		dayTimePlusBtn.setFont(new Font("aria", Font.BOLD, 25));
		dayTimeUIContainerPanel.add(dayTimePlusBtn);
				
		//스크롤 추가
		JScrollPane jsp = new JScrollPane(dayTimeUIContainerPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Panel.background")));
		GridBagConstraints gbcDayAndTime = new GridBagConstraints();
		gbcDayAndTime.gridwidth = 10;
		gbcDayAndTime.gridheight = 3;
		gbcDayAndTime.insets = new Insets(0, 0, 5, 35);
		gbcDayAndTime.fill = GridBagConstraints.BOTH;
		gbcDayAndTime.gridx = 1;
		gbcDayAndTime.gridy = 9;
		jsp.putClientProperty("isDeleteWhenReSelectRadioBtn", true);
		add(jsp, gbcDayAndTime);
	}
	
	//강의 일정 체크 박스를 체크하면 호출되는 함수
	//기타 일정 체크 박스의 체크상태를 해제
	//강의명, 교수명, 장소, 요일 및 시간을 입력할 수 있는 입력란을 보여준다.
	//요일 및 시간을 삭제할 수 있는 버튼과 
	//요일 및 시간을 추가할 수 있는 버튼을 보여준다.
	public void selectLecture() {
		Component[] components = getComponents();
		this.timeLines = new ArrayList<DayTimeUI>();
		//panel에서 모든 컴포넌트 중에 강의일정 검색 후 선택
		for(int i=0; i<components.length;i++) {
			if(components[i] instanceof JRadioButton) {
				//기타일정 Radio button에 select
				JRadioButton radioBtn = (JRadioButton)components[i];
				if(radioBtn.getText().equals("강의일정")) {
					radioBtn.setSelected(true);
				}
			}
			
			//isDeleteWhenReSelectRadioBtn -> 강의일정, 기타일정과 같은 Radio Button이 변경될때 마다 기존에 있던 삭제해야할 Component를 의미 
			//만약 기존에 강의일정이 선택되어있다면 강의일정을 선택했을때 생성된 Component들을 삭제
			if(components[i] instanceof JComponent) {
				JComponent jComponent = (JComponent)components[i];
				if(jComponent.getClientProperty("isDeleteWhenReSelectRadioBtn") != null){
					boolean isDelete = (boolean)jComponent.getClientProperty("isDeleteWhenReSelectRadioBtn");
					remove(components[i]);
				}
			}
		}
		
		//수업명 label 생성
		JLabel lblNewLabel = new JLabel("수업명");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 5;
		add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.putClientProperty("isDeleteWhenReSelectRadioBtn", true);
		
		//수업명 field 생성
		JTextField textField = new JTextField();
		textField.setName("title");// 필드 이름 설정
		GridBagConstraints gbc_textField = new GridBagConstraints();
		textField.setBorder(new MatteBorder(0, 0, 1, 0, UIManager.getColor ("Button.light")));
		gbc_textField.gridwidth = 6;
		gbc_textField.insets = new Insets(0, 10, 5, 45);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 5;
		add(textField, gbc_textField);
		textField.setColumns(10);
		textField.putClientProperty("isDeleteWhenReSelectRadioBtn", true);
		
		
		//교수명 label 생성
		JLabel lblNewLabel_1 = new JLabel("교수명");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 6;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		lblNewLabel_1.putClientProperty("isDeleteWhenReSelectRadioBtn", true);
		
		//교수명 field 생성
		JTextField textField_1 = new JTextField();
		textField_1.setName("profName");// 필드 이름 설정
		textField_1.setColumns(10);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		textField_1.setBorder(new MatteBorder(0, 0, 1, 0, UIManager.getColor ("Button.light")));
		gbc_textField_1.gridwidth = 6;
		gbc_textField_1.insets = new Insets(0, 10, 5, 45);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 6;
		add(textField_1, gbc_textField_1);
		textField_1.putClientProperty("isDeleteWhenReSelectRadioBtn", true);
		
		//장소 label 생성
		JLabel lblNewLabel_1_1 = new JLabel("장소");
		GridBagConstraints gbc_lblNewLabel_1_1 = new GridBagConstraints();
		gbc_lblNewLabel_1_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1_1.gridx = 1;
		gbc_lblNewLabel_1_1.gridy = 7;
		add(lblNewLabel_1_1, gbc_lblNewLabel_1_1);
		lblNewLabel_1_1.putClientProperty("isDeleteWhenReSelectRadioBtn", true);
		
		//장소 field 생성
		JTextField textField_2 = new JTextField();
		textField_2.setName("location");// 필드 이름 설정
		textField_2.setColumns(10);
		textField_2.setBorder(new MatteBorder(0, 0, 1, 0, UIManager.getColor ("Button.light")));
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.gridwidth = 6;
		gbc_textField_2.insets = new Insets(0, 10, 5, 45);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 7;
		add(textField_2, gbc_textField_2);
		textField_2.putClientProperty("isDeleteWhenReSelectRadioBtn", true);
		
		//DayAndTime UI Container
		JPanel dayTimeUIContainerPanel = new JPanel();
		
		//DayAndTime UI Container 초기화
		dayTimeUIContainerInit(dayTimeUIContainerPanel);
	}
	
	//기타일정 체크 박스를 선택하면 호출되는 함수이다.
	//강의 일정 체크박스의 체크상태를 해제한다.
	//일정명, 요일및시간 입력란을 보여준다.
	//요일 및 시간을 삭제할 수 있는 버튼과 요일 및 시간을 추가할 수 있는 버튼을 보여준다. 
	public void selectOtherSchedule() {
		Component[] components = getComponents();
		this.timeLines = new ArrayList<DayTimeUI>();
		//panel에서 모든 컴포넌트 중에 기타일정 검색 후 선택 
		for(int i=0; i<components.length;i++) {	
			if(components[i] instanceof JRadioButton) {
				//기타일정 Radio button에 select
				JRadioButton radioBtn = (JRadioButton)components[i];
				if(radioBtn.getText().equals("기타일정")) {
					radioBtn.setSelected(true);
				}
			}
			
			//isDeleteWhenReSelectRadioBtn -> 강의일정, 기타일정과 같은 Radio Button이 변경될때 마다 기존에 있던 삭제해야할 Component를 의미 
			//만약 기존에 강의일정이 선택되어있다면 강의일정을 선택했을때 생성된 Component들을 삭제
			if(components[i] instanceof JComponent) {
				JComponent jComponent = (JComponent)components[i];
				if(jComponent.getClientProperty("isDeleteWhenReSelectRadioBtn") != null){
					boolean isDelete = (boolean)jComponent.getClientProperty("isDeleteWhenReSelectRadioBtn");
					remove(components[i]);
				}
			}
		}
		
		//일정명 label 생성
		JLabel lblNewLabel = new JLabel("일정명");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 5;
		add(lblNewLabel, gbc_lblNewLabel);
		lblNewLabel.putClientProperty("isDeleteWhenReSelectRadioBtn", true);
		
		//일정명 field 생성
		JTextField textField = new JTextField();
		textField.setName("title"); // 필드 이름 설정
		GridBagConstraints gbc_textField = new GridBagConstraints();
		textField.setBorder(new MatteBorder(0, 0, 1, 0, UIManager.getColor ("Button.light")));
		gbc_textField.gridwidth = 6;
		gbc_textField.insets = new Insets(0, 10, 5, 45);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 5;
		add(textField, gbc_textField);
		textField.setColumns(10);
		textField.putClientProperty("isDeleteWhenReSelectRadioBtn", true);
		
		//DayAndTime UI Container
		JPanel dayTimeUIContainerPanel = new JPanel();
		
		//DayAndTime UI Container 초기화
		dayTimeUIContainerInit(dayTimeUIContainerPanel);

	}
	
	//일정추가 버튼을 클릭하면 호출되는 함수이다.
	//입력되어있는 시간 정보를 가져와서 checkDuplication함수의 인자로 넘긴다.
	//true를 반환 받으면 "시간이 겹칩니다."팝업창을 띄우고 함수를 종료한다.
	//false를 반환 받으면 선택되어 있는 일정 종류를 확인한다.
	//'강의 일정'의 경우 '강의명, 교수명, 장소, 요일 및 시간'을 '기타 일정'의 경우 '일정명, 요일 및 시간'
	//정보를 가져와서 새로운 Schedule객체에 저장한다.
	//서로 생성된 Schedule객체를 allSchedule에 추가한다.
	//leftTableUI의 printSchedule 함수를 호출하여 새로운 일정을 시간표에 추가해 보여 줄 수 있도록 한다.
	public void addScheduleBtnClick() {
		
		//현재 추가할려고 하는 일정에서 기존에 저장되어있던 일정과 겹치는게 있다면
		//알림 표시 후 함수 종료 
		if(checkDuplication()) {
			JOptionPane.showMessageDialog(null, "시간이 겹칩니다.", "일정추가 에러 메세지", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		Component[] components = getComponents();
		boolean isLecture = false; //강의일정이 선택되어 있는지 아니면 기타일정이 선택되어있는지 상태를 저장하는 변수

		//panel에서 모든 컴포넌트 중에 "강의일정" 검색 후 선택 
		for(int i=0; i<components.length;i++) {	
			if(components[i] instanceof JRadioButton) {
				if(((JRadioButton)components[i]).getText().equals("강의일정")) {
					
					//현재 강의일정이 선택되어 있다면 
					if(((JRadioButton)components[i]).isSelected()) {
						isLecture = true;
						break;
					}
				}
			}
		}
		
		String title="", profName="", location="";
		//강의일정이 선택된 경우 
		if(isLecture) {
			for(int i=0; i<components.length;i++) {	
				if(components[i] instanceof JTextField) {
					JTextField field = (JTextField)components[i];
					String fieldName = field.getName();
					
					if(fieldName.equals("title")) {
						title = field.getText().strip();
					}else if(fieldName.equals("profName")) {
						profName = field.getText().strip();
					}else if(fieldName.equals("location")) {
						location = field.getText().strip();
					}
				}
			}
		}else {//기타일정이 선택된 경우 
			for(int i=0; i<components.length;i++) {	
				if(components[i] instanceof JTextField) {
					JTextField field = (JTextField)components[i];
					String fieldName = field.getName();
					
					if(fieldName.equals("title")) {
						title = field.getText().strip();
					}
				}
			}
		}
		
		if(isLecture) {
			if(title.equals("")) {
				JOptionPane.showMessageDialog(null, "수업명을 입력해주세요", "일정추가 에러 메세지", JOptionPane.WARNING_MESSAGE);
				return;
			}else if(profName.equals("")) {
				JOptionPane.showMessageDialog(null, "교수명을 입력해주세요", "일정추가 에러 메세지", JOptionPane.WARNING_MESSAGE);
				return;
			}else if(location.equals("")) {
				JOptionPane.showMessageDialog(null, "장소명을 입력해주세요", "일정추가 에러 메세지", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}else {
			if(title.equals("")) {
				JOptionPane.showMessageDialog(null, "일정명을 입력해주세요", "일정추가 에러 메세지", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
		
		ArrayList<DayAndTime> dayAndTimes = new ArrayList<DayAndTime>();
		for(int i=0; i<timeLines.size(); i++) {
			DayAndTime dayTime = timeLines.get(i).getDayAndTimeObject();
			dayAndTimes.add(dayTime);
		}
		
		Schedule scheldule = new Schedule(0, title, profName, location, dayAndTimes);
		allSchedule.add(scheldule);
		
		
		leftUI.printSchedule();
	}
	
	
	//allSchedule에 저장되어 있는 일정들의 시간과 인자로 받은 시간이 겹친다면 true를 반환한다. 
	public Boolean checkDuplication() {
		for(int k=0; k<timeLines.size(); k++) {
			DayAndTime dayTime = timeLines.get(k).getDayAndTimeObject();
			String day = dayTime.day;
			int startTime = dayTime.startTime;
			int endTime = dayTime.endTime;
			
			for(int i=0; i < allSchedule.size(); i++) {
				ArrayList<DayAndTime> dayAndTimes = allSchedule.get(i).getDayAndTime();

				for(int j=0; j<dayAndTimes.size(); j++) {
					if(dayAndTimes.get(j).day == day) {
						DayAndTime nowDay = dayAndTimes.get(j); //기존에 있던 일정 
						
						
						if(nowDay.startTime <= startTime &&  startTime < nowDay.endTime) {
							return true;
						}
						else if(nowDay.startTime < endTime &&  endTime <= nowDay.endTime) {
							return true;
						}else if(startTime <= nowDay.startTime && nowDay.startTime <= endTime) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public void addTimeLineBtnClick() {
		DayTimeUI newDayTimeUI = new DayTimeUI();
		timeLines.add(newDayTimeUI);
		
		//
		dayTimeUIPanel.add(newDayTimeUI);
		
		//x버튼 추가 
		JButton dayTimeXBtn = new JButton(" x");
		dayTimeXBtn.setBackground(UIManager.getColor ( "Panel.background" ));
		dayTimeXBtn.setBorder(null);
		dayTimeXBtn.setFont(new Font("aria", Font.BOLD, 15));
		dayTimeXBtn.putClientProperty("DayTimeUIInstance", newDayTimeUI);
		GridBagConstraints gbcDayTimeXBtn = new GridBagConstraints();
		gbcDayTimeXBtn.gridx = 6;
		gbcDayTimeXBtn.gridy = 0;
		newDayTimeUI.add(dayTimeXBtn, gbcDayTimeXBtn);
				
		//x버튼 action추가
		dayTimeXBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComponent button = (JComponent)e.getSource();
				DayTimeUI dayTime = (DayTimeUI)button.getClientProperty("DayTimeUIInstance");
				dayTimeUIPanel.remove(dayTime);
				timeLines.remove(dayTime);
				revalidate();
				repaint();
			}
		});
		
		revalidate();
		repaint();
	}
	
	//Seq(5) 알림설정 KCH
	public void alarmOnOffBtnClick() {
		alarm.changeAlarmState();
	}

}
