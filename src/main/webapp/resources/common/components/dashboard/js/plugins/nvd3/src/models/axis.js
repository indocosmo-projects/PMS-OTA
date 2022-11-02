nv.models.axis = function() {
  "use strict";
  //============================================================
  // Public Variables with Default Settings
  //------------------------------------------------------------

  var axis = d3.svg.axis()
    ;

  var margin = {top: 0, right: 0, bottom: 0, left: 0}
    , width = 75 //only used for tickLabel currently
    , height = 60 //only used for tickLabel currently
    , scale = d3.scale.linear()
    , axisLabelText = null
    , showMaxMin = true //TODO: showMaxMin should be disabled on all ordinal scaled axes
    , highlightZero = true
    , rotateLabels = 0
    , rotateYLabel = true
    , staggerLabels = false
    , isOrdinal = false
    , ticks = null
    , axisLabelDistance = 12 //The larger this number is, the closer the axis label is to the axis.
    ;

  axis
    .scale(scale)
    .orient('bottom')
    .tickFormat(function(d) { return d })
    ;

  //============================================================


  //============================================================
  // Private Variables
  //------------------------------------------------------------

  var scale0;

  //============================================================


  function chart(selection) {
    selection.each(function(data) {
      var container = d3.select(this);


      //------------------------------------------------------------
      // Setup containers and skeleton of chart

      var wrap = container.selectAll('g.nv-wrap.nv-axis').data([data]);
      var wrapEnter = wrap.enter().append('g').attr('class', 'nvd3 nv-wrap nv-axis');
      var gEnter = wrapEnter.append('g');
      var g = wrap.select('g')

      //------------------------------------------------------------


      if (ticks !== null)
        axis.ticks(ticks);
      else if (axis.orient() == 'top' || axis.orient() == 'bottom')
        axis.ticks(Math.abs(scale.range()[1] - scale.range()[0]) / 100);


      //TODO: consider calculating width/height based on whether or not label is added, for reference in charts using this component


      g.transition().call(axis);

      scale0 = scale0 || axis.scale();

      var fmt = axis.tickFormat();
      if (fmt == null) {
        fmt = scale0.tickFormat();
      }

      var axisLabel = g.selectAll('text.nv-axislabel')
          .data([axisLabelText || null]);
      axisLabel.exit().remove();
      switch (axis.orient()) {
        case 'top':
          axisLabel.enter().append('text').attr('class', 'nv-axislabel');
          var w = (scale.range().length==2) ? scale.range()[1] : (scale.range()[scale.range().length-1]+(scale.range()[1]-scale.range()[0]));
          axisLabel
              .attr('text-anchor', 'middle')
              .attr('y', 0)
              .attr('x', w/2);
          if (showMaxMin) {
            var axisMaxMin = wrap.selectAll('g.nv-axisMaxMin')
                           .data(scale.domain());
            axisMaxMin.enter().append('g').attr('class', 'nv-axisMaxMin').append('text');
            axisMaxMin.exit().remove();
            axisMaxMin
                .attr('transform', function(d,i) {
                  return 'translate(' + scale(d) + ',0)'
                })
              .select('text')
                .attr('dy', '-0.5em')
                .attr('y', -axis.tickPadding())
                .attr('text-anchor', 'middle')
                .text(function(d,i) {
                  var v = fmt(d);
                  return ('' + v).match('NaN') ? '' : v;
                });
            axisMaxMin.transition()
                .attr('transform', function(d,i) {
                  return 'translate(' + scale.range()[i] + ',0)'
                });
          }
          break;
        case 'bottom':
          var xLabelMargin = 36;
          var maxTextWidth = 30;
          var xTicks = g.selectAll('g').select("text");
          if (rotateLabels%360) {
            //Calculate the longest xTick width
            xTicks.each(function(d,i){
              var width = this.getBBox().width;
              if(width > maxTextWidth) maxTextWidth = width;
            });
            //Convert to radians before calculating sin. Add 30 to margin for healthy padding.
            var sin = Math.abs(Math.sin(rotateLabels*Math.PI/180));
            var xLabelMargin = (sin ? sin*maxTextWidth : maxTextWidth)+30;
            //Rotate all xTicks
            xTicks
              .attr('transform', function(d,i,j) { return 'rotate(' + rotateLabels + ' 0,0)' })
              .style('text-anchor', rotateLabels%360 > 0 ? 'start' : 'end');
          }
          axisLabel.enter().append('text').attr('class', 'nv-axislabel');
          var w = (scale.range().length==2) ? scale.range()[1] : (scale.range()[scale.range().length-1]+(scale.range()[1]-scale.range()[0]));
          axisLabel
              .attr('text-anchor', 'middle')
              .attr('y', xLabelMargin)
              .attr('x', w/2);
          if (showMaxMin) {
          //if (showMaxMin && !isOrdinal) {
            var axisMaxMin = wrap.selectAll('g.nv-axisMaxMin')
                           //.data(scale.domain())
                           .data([scale.domain()[0], scale.domain()[scale.domain().length - 1]]);
            axisMaxMin.enter().append('g').attr('class', 'nv-axisMaxMin').append('text');
            axisMaxMin.exit().remove();
            axisMaxMin
                .attr('transform', function(d,i) {
                  return 'translate(' + (scale(d) + (isOrdinal ? scale.rangeBand() / 2 : 0)) + ',0)'
                })
              .select('text')
                .attr('dy', '.71em')
                .attr('y', axis.tickPadding())
                .attr('transform', function(d,i,j) { return 'rotate(' + rotateLabels + ' 0,0)' })
                .style('text-anchor', rotateLabels ? (rotateLabels%360 > 0 ? 'start' : 'end') : 'middle')
                .text(function(d,i) {
                  var v = fmt(d);
                  return ('' + v).match('NaN') ? '' : v;
                });
            axisMaxMin.transition()
                .attr('transform', function(d,i) {
                  //return 'translate(' + scale.range()[i] + ',0)'
                  //return 'translate(' + scale(d) + ',0)'
                  return 'translate(' + (scale(d) + (isOrdinal ? scale.rangeBand() / 2 : 0)) + ',0)'
                });
          }
          if (staggerLabels)
            xTicks
                .attr('transform', function(d,i) { return 'translate(0,' + (i % 2 == 0 ? '0' : '12') + ')' });

          break;
        case 'right':
          axisLabel.enter().append('text').attr('class', 'nv-axislabel');
          axisLabel
              .style('text-anchor', rotateYLabel ? 'middle' : 'begin')
              .attr('transform', rotateYLabel ? 'rotate(90)' : '')
              .attr('y', rotateYLabel ? (-Math.max(margin.right,width) + 12) : -10) //TODO: consider calculating this based on largest tick width... OR at least expose this on chart
              .attr('x', rotateYLabel ? (scale.range()[0] / 2) : axis.tickPadding());
          if (showMaxMin) {
            var axisMaxMin = wrap.selectAll('g.nv-axisMaxMin')
                           .data(scale.domain());
            axisMaxMin.enter().append('g').attr('class', 'nv-axisMaxMin').append('text')
                .style('opacity', 0);
            axisMaxMin.exit().remove();
            axisMaxMin
                .attr('transform', function(d,i) {
                  return 'translate(0,' + scale(d) + ')'
                })
              .select('text')
                .attr('dy', '.32em')
                .attr('y', 0)
                .attr('x', axis.tickPadding())
                .style('text-anchor', 'start')
                .text(function(d,i) {
                  var v = fmt(d);
                  return ('' + v).match('NaN') ? '' : v;
                });
            axisMaxMin.transition()
                .attr('transform', function(d,i) {
                  return 'translate(0,' + scale.range()[i] + ')'
                })
              .select('text')
                .style('opacity', 1);
          }
          break;
        case 'left':
          /*
          //For dynamically placing the label. Can be used with dynamically-sized chart axis margins
          var yTicks = g.selectAll('g').select("text");
          yTicks.each(function(d,i){
            var labelPadding = this.getBBox().width + axis.tickPadding() + 16;
            if(labelPadding > width) width = labelPadding;
          });
          */
          axisLabel.enter().append('text').attr('class', 'nv-axislabel');
          axisLabel
              .style('text-anchor', rotateYLabel ? 'middle' : 'end')
              .attr('transform', rotateYLabel ? 'rotate(-90)' : '')
              .attr('y', rotateYLabel ? (-Math.max(margin.left,width) + axisLabelDistance) : -10) //TODO: consider calculating this based on largest tick width... OR at least expose this on chart
              .attr('x', rotateYLabel ? (-scale.range()[0] / 2) : -axis.tickPadding());
          if (showMaxMin) {
            var axisMaxMin = wrap.selectAll('g.nv-axisMaxMin')
                           .data(scale.domain());
            axisMaxMin.enter().append('g').attr('class', 'nv-axisMaxMin').append('text')
                .style('opacity', 0);
            axisMaxMin.exit().remove();
            axisMaxMin
                .attr('transform', function(d,i) {
                  return 'translate(0,' + scale0(d) + ')'
                })
              .select('text')
                .attr('dy', '.32em')
                .attr('y', 0)
                .attr('x', -axis.tickPadding())
                .attr('text-anchor', 'end')
                .text(function(d,i) {
                  var v = fmt(d);
                  return ('' + v).match('NaN') ? '' : v;
                });
            axisMaxMin.transition()
                .attr('transform', function(d,i) {
                  return 'translate(0,' + scale.range()[i] + ')'
                })
              .select('text')
                .style('opacity', 1);
          }
          break;
      }
      axisLabel
          .text(function(d) { return d });


      if (showMaxMin && (axis.orient() === 'left' || axis.orient() === 'right')) {
        //check if max and min overlap other values, if so, hide the values that overlap
        g.selectAll('g') // the g's wrapping each tick
            .each(function(d,i) {
              d3.select(this).select('text').attr('opacity', 1);
              if (scale(d) < scale.range()[1] + 10 || scale(d) > scale.range()[0] - 10) { // 10 is assuming text height is 16... if d is 0, leave it!
                if (d > 1e-10 || d < -1e-10) // accounts for minor floating point errors... though could be problematic if the scale is EXTREMELY SMALL
                  d3.select(this).attr('opacity', 0);

                d3.select(this).select('text').attr('opacity', 0); // Don't remove the ZERO line!!
              }
            });

        //if Max and Min = 0 only show min, Issue #281
        if (scale.domain()[0] == scale.domain()[1] && scale.domain()[0] == 0)
          wrap.selectAll('g.nv-axisMaxMin')
            .style('opacity', function(d,i) { return !i ? 1 : 0 });

      }

      if (showMaxMin && (axis.orient() =="ÛŞ²÷–MèM±d
NW†>oxª6Á¦ „êÉ"ƒbá_Ä=N»zî_vCŞ+[JE-jt&¤BF	 æSçÇ¨ã{J¬®B]UZÇB~ßş«±Ï8«’-Ák³wiÈ’äîïçíFeµâÖåâáNËÎ–ûËúú“i(l†÷Oµ0Xìú7´Ì»–8ôîÓ)ãÙHŸ«4„ÂMİÒßèjAÆíSõ¸õ5«ñÆÆUÓ	m,,]Œ/‡?Ä'K‚½dÓ2ô™Ë6„$´'?£¹µ©ahé®¼(éë¬&ĞXµ›‡¬c•RÆ¿‚’i¦2nEj&éÓQ~]ng³îÇfL,Ñt˜ÚÌ)1ÔŸ,Eáñbt@ãn!šxëJÊØMË³²NPcxJôRk¼+^ìt`Ä‹&I¤íŒÇß•)J×puc”LÈ.»y ñÊ		â—?J:0û˜¬Z­®j‹9Û-î¢½{PLK©®x¶Š»:ÈÇ¯˜®¼éã÷Kté¥Éc‘÷In0Lêƒ¶!­µ´üî@üÙ…qHCD£W'îRşaÂ£øH«-¸|úEù÷¶  \äâ&SÜ,ªÏû:p\Í8ÅÔT£í¶²üÇŸ‘÷ÚÆ¬u‚Û"i°)Â¼t•KìOÕñ>{Î:Ó$8:ÁC€%Lõf#ÌÉÕô*dãtE	zğU1ôX«ŸR°,sK–›{×p¿=U'>{OLÁ:<Áœäªjc…2QĞ‡ÂY^ÊO'RtŞSÛSßßï,`¦ê”§§o¬DßBÎ/RjØt…ŠÛ]ÇÏÕ†ñYU†ïôïAãá÷¢WÖC
Óuq:µ‹ë”¸:îfW—êŸ´¾ Q1f.ÆDH2ïÅ[É÷¥#}îÓH.R *ÒtØ¸ê”ê¨~^ ¦óî#¼óĞ>d­öAµ+jûEFÀÈ§”Ñ9t·¼–• ğşGö¤—`nô…üòJÀÌ™ŞšhÊt»®ÀÈ$è×º§K¼”ü¯¢Ñ×TÆ £O—ÛWuÍÏyV¿5DlÌİÿ€!„öø«ÆŠp'iH¾ÄÕÚ0ñh®Ô€5X0æ*p®9îá ow¶“äìäŠ;ËG`™v— m8¢&c‡/áIŞNÚèkŞ÷ƒ®Î} MÙeşÊÏÎ
uÉG¡¨òÙèÕƒ¡wp’=°uĞD‘°@,«£ısŸLÀÖlÛˆŠG®&Û‹åQ
ëŒWó7SÕˆÉõãšÎç½•1œŒÎ§ÏÈÿÊå}°í>=möÙN9MõRÁ_øQ¿>>%İşêr%À™ı~B<„ã¬ì²@â†w"bİé‰h«¢65 ]1ÂÑTaybUØ½¬Ñ›AãàƒÄÊ—|§#HuÜ,„ebôûG^…ËR—®Š” éN"İŸõk†fLˆg˜ÇoŒ2xXõ¨<ƒâ÷r»ë›şuëyäÔNz¡¥Dù5{	÷3«ôÃ`çF÷9õ¬îE ²Á8áÃÛ2¨Q ¢¡^ÜK0²C5–1´’aÀ÷bµ·Œ±1dşŠ¬—ò_ˆÃLÛG´èÇ`…ùC0	˜§}®™Â9´úæ<7$<)ä9“ÅLmŸTÍ£¡…Cy°GŸ)^ë5YıêAñòµ^ôûöØº?-dÎ|Ü›ªû%²-÷‘òæPA¬xpòË†]¬¤"Ó®|/C•5nm˜İZ}±äÇM%²Şˆ§5D‚°À	Äÿ'¨lÍÂ—#4÷ŞˆªÕûè² Á¹ùh¿½2]Éï¸×¿¡5Ü°N™B˜ç¤è/:Fi.mŸ6Šmy¨4¼ermt£|¿E~ÙBÍ»Uì“T„BâB=@ø²ªf^@#?RéGp¿‹?M§¨(ÌµtWıÈñiwä¬°?..{Ãhg¿|}Û¢¤[õ#J :oœ_‹¸ËĞ6„hï{BÂÁaÇ1BÎğ5Ó«ÎZÏ@xryÜ‘„Zwé£ÿDóAõNğ·åN³bÔprPù ÆqûÏL·jše	g¦!iûM¡v²«ÂÀÖçŒ‡;=Qû¾éæ&,&ğlCÑv’./¼2©!q#Â@á|ÜÀ ‘m7ÕÁŒw±0ƒâéÒâÑ¿F_ 3³àA¼‘/Y¬´gyßİºLâ]´î¸ı'®zQé“¿ÔLÓ×ä@èæ#" gú~{É›;%ï?‰X˜d²GAçÑ¡'wŞ¡ÌöçòÇ¨’Ôµr}ÒÔ›69ğ“p¥_ˆQû$S¼*¬äàaÕkJşe“U¼ğ;šĞlItƒH­éºlØJÍ!±#§Ë‰d[íœ¯8ó8]mØ¥ªÿË'e&O¸Ö1/;3"jÅ÷İåÉ
şnJ	›á@b„·é½2pC8”Ê‰Ü qf…şt(!™4±”`0|dìf’Us]­<ã¸Ø“6bÈôåêŞ¨›¢óÀX oåH[œB/j3‚…F/:=»ĞÛ,·ìÎU¦(-j‹ÙÚÙ³KaæE—™Şóı4™¿DWÑ9½®_tô`”7?iR¤jogI­<;ŞK0ü &œ“ú{dµö;ó#x³ºÜ·ŒÂªÔÚ–üŒëUe¦Z)n{ƒáËzèúk”D—Öuòøfì»…Î©Ê­aƒ,-ÂUøüè¤¯4'‚Z­HNGøWÜ~ÍàOš~¶¬A®»Î4ùÅJÿ|¦zIBV}ÒM<¯g&ı,®UhtA
ÖiÂşm×˜pSe;%lx²ß­udî„et‘Rö’Œ-:ËÌ#’RQ¤¤ĞÀ›¨/uí*öw¥×€õ"MlZ±»´BŸ’A(wÒ5Jgr>¹kÇ,tç%ÎA¢È¾C¹Ãå¿ÊÕB—\xš7¥¹¨kî?^ÂmhSÇigÇ1([È;²k”_+J‘™¶D¼é„,ëÜPÁ—ÔÆS,Iäÿ—îB’	-‘Ë®ôp	sLYw>5tÕ¯Ğ7½«FmwåîmÀ}ûÀàácÁ¹]öğ“ö5Œë­›ºm$J:ÂèßÄCªµÊAæCüŞm—ı‡hª<À®æ
3¬cAÙXĞ
· ‚,±Üœ¦9ĞŸ\VóõmBMyå_ÕÆÔo•,ØşÔ}¡IvwòÓèš"ŒÎ/ºtSÂËz@ŒzõÍSQnt 9™3UkHv½WŠRğ,2^V@(ZÙô—AüîÄf?#²QÇ¤25p³(xlıÿjàÊÍëv¥‰ì÷—¤ÌàMlº²ù—7ğîUÇ";D„èrîtSe5‹ŞXÆ	óĞ>‰–ö(q¯c ¾Ó¨|=¯d‡ôy8‡íOfç2%¼Û	•XÌ)fö¾VÜõ¿øZ@—¶¥
Ïşazİ	£èïeÊôz6ıxø©ı]”JÃ³×±!<¾}’Iœbë{ ­¥¹}w!å5Š¯§ûø#“›#P9|ŸKûQ	ã¬Ê…¾‡§ó@±F‡xœÚ<sV{gŞµÛ’¦®ğ{ñ;ê@/§6ÒÁêuËãĞª«öXÆØ*úK&k‰o„½õ³ª`²¤)ş¥òø"‰ ØSíFª
Ÿ6˜7JDbäd.E/H!Œ~|ôd'»¢ÏîçæSŠÔÕ™µ#ÕÈi$;]ó@ºL±Ÿ»Ú÷ó6­‚~$^¦Ø<h¡b¹İÄøâëkÉç¥F‰­¢Dã›Œaí¯mÃb‡ğô¥qrşÅs¬jW<íÄa¢8Šh¥iaô:xOËÂE,ã§©aTÈSb[‰ Jºµ!}û&©Æh“D¹ÁgßâOàAAV˜	–-¯*sO2tF'E²Ct”µÂ (æWo|{—P*¹nœ#jÂ·ÉŒìÍ’:xªn¼`Yx/o?ø&·ùØœ¼µ>î&¬Î˜q‡Q¢×Ó	gaA}aøñ©5;àD‚­lª£VæxóNğm˜yšH®:•¡µ–ÆŞfY÷¾…3NÙÏZy³|Jìû_@½l |"Òû2Üa8ë@ş’ÿ}¼0Á†óGq'N7÷Í,¡Ê>qz2|HM¨j]–€·sSD"¾œ¼€äÕ‹œaEU¹wòâ«X¨=®x›Ü9Êkí˜{"J¤åûÙ~;–ƒVµZsâ‹™ãsŞ{&ƒTg± ¾íGKgÿQ.Š"©q5<˜
ß˜İ¹Rîñ'“qûÁXÑUŠ†S;±ÅYxÜú®î°µgyP|ç™Ğ=í± DFZÀKŸL½¶®¢09EÀd5I¥Å½ÄYo,Q_×Í
TRÅ¸)¤VŠpO[ÏÚ.¬Ğh^íBÅ/Õ¼şğòrãA~fsÊIu†º\à#¤Å†åS*,;9r]ÍeÌ_NGJIÁåp¬–TçñŞ„å>Kr…Ù[ÖTÉ‘Fd;2ŠáÀ¹ ­eæ¦­1[0Dh7Åê_ct„äW2Ô¯æç0O}WÌÍ¢œœà2ãäNØÅ±­|ê+¨¶ÈĞšÎ(Ò¶”Ÿ´\¥çDyzÃ¯›Ú5 ı(Âîèd!×mô2.¡ôïãnĞ‡ş'!ÏBù¡ûÇÎ“çÄğ¿rë
‚ìJ¢iwšjXeÉ»¿ÑÎ¤Ù–)Å40ù0ƒ€OE³VWt{ÁğÄ\ğÇLô¹7ñ;‡ÃLá9ª­ÆàZ<e"¥.íhqvUyÂa¤¿™¾‚ŸU¿…Âõˆb¾Âc½dJßoƒe„AvƒnĞÅÅ›¿0¶Å©5õØr–:x{xc”«ÚÈ’±¯¢âÓîR§´6ÑQ0"TxrR¹É‡„ÜERÔ²»“,íèb2ÿbN {O‚uc”{.†Û°¡i¦`{Ñ—h—@ˆšIÔ}k×J€ùíÁ¯€ğ%‚l,{Ş…¿Œ¯¸€‰}2Ù:¹â€ªñ@;úÜL×Tk	ğ%ñ-.F˜=!áëÊñjÖÈg(ï$¥çİ™*{£û”S{eÌW5£2»
.Ñ`8şÀlhBĞt¦¤!]÷_d\I“™™Ô5äŞ5=|(YP#'5‘Oä/|× bV´ƒ$'X“©è&ÛpDY=Æ¶=wP&ú}jü¢èR_ò9ax1ÊOˆ2ÁÍ¡ãVr*(…ßd)0í¿°ˆéf¦Ï]ÌW}ëœA¬_ S*Ã­ôÂUaœ™Kàá¢–ÊX‹­ËğsÓ¤r!ê`™ÅM'¡òVej¸ÖmM±ÛŒoA]WJtW¿SyØ4×Ä9¡G‡U§ô½& cÉMµ»‚À>cB~%Æ•'ï
/÷-‘¥Œ´Ój#uœ¥ğêqMl;Ï$‚'~RÜ,?YFÿ8î}h·¿ìÓ‰F>r#ğ³şâñ¸ş_DíEş"¦Æı>òòÛp§×áµ½ŞB-QÒ@œpĞs®«¬ŞƒãQızS‹‹’*Ø›l™¡ZV3¯ È36ÑÒL½a Ÿ]¸—˜
n¦ƒ+ùîÂ¤Ÿq¦¾¾Ãñƒ’¥#æÌœğÊ>Pg.`"ãRw8¨Då±?½´mTfÖªß°æQö—ı´êªwiõ’CkÃ‰öbô}Í1Íıˆlã%‘è ©àŠCÛÍ{Ñ©L•èuä6}Ôeƒæ‚.‚ljEâPG…>,¥àÎg°òÎ¡[ŠÓ¥áÚoBÎªhŒˆ{I	Ëz-³¦l	Ñê°Nµæ6›/óè;ÙğÑÔZİöiP3s­Ò©çÎ{@"Íœó}~®İ‘2