var Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 ];// 加权因子   
var ValideCode = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];// 身份证验证位值.10代表X   
function IdCardValidate(idCard) {
    var a_idCard = idCard.split("");// 得到身份证数组   
    if(isValidityBrithBy18IdCard(idCard)&&isTrueValidateCodeBy18IdCard(a_idCard)){   
        return true;   
    }else {   
    	return false;   
    }      
}

/**  
 * 判断身份证号码为18位时最后的验证位是否正确  
 * @param a_idCard 身份证号码数组  
 * @return  
 */  
function isTrueValidateCodeBy18IdCard(a_idCard) {   
    var sum = 0; // 声明加权求和变量   
    if (a_idCard[17] == 'x') {   
         // 将最后位为x的验证码替换为X方便后续操作   
    	a_idCard[17]="X";
    }   
    for ( var i = 0; i < 17; i++) {   
        sum += Wi[i] * a_idCard[i];// 加权求和   
    }   
    valCodePosition = sum % 11;// 得到验证码所位置   
    if (a_idCard[17] == ValideCode[valCodePosition]) {   
        return true;   
    } else {   
        return false;   
    }   
}   

/**  
 * 通过身份证判断是男是女  
 * @param idCard 18位身份证号码   
 * @return '1'-女、'0'-男  
 */  
function getSex(idCard){   
    idCard = trim(idCard.replace(/ /g, ""));// 对身份证号码做处理。包括字符间有空格。   
    if(idCard.substring(14,17)%2==0){   
        return '1';   
    }else{   
        return '0';   
    }   
}

/**  
 * 验证18位数身份证号码中的生日是否是有效生日  
 * @param idCard 18位书身份证字符串  
 * @return  
 */  
function isValidityBrithBy18IdCard(idCard18){   
   var year =  idCard18.substring(6,10);   
   var month = idCard18.substring(10,12);   
   var day = idCard18.substring(12,14);   
   var temp_date = new Date(year,parseFloat(month)-1,parseFloat(day));   
   // 这里用getFullYear()获取年份，避免千年虫问题   
   if(temp_date.getFullYear()!=parseFloat(year)   
         ||temp_date.getMonth()!=parseFloat(month)-1   
         ||temp_date.getDate()!=parseFloat(day)){   
           return false;   
   }else{   
       return true;   
   }   
}   

function getBrithDay(idCard18){   
	var year =  idCard18.substring(6,10);   
	var month = idCard18.substring(10,12);   
	var day = idCard18.substring(12,14);
	var crsq=year+"-"+month+"-"+day;
	return crsq;
}   



//去掉字符串头尾空格   
function trim(str) {   
    return str.replace(/(^\s*)|(\s*$)/g, "");   
} 