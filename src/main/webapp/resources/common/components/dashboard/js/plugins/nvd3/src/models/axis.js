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

      if (showMaxMin && (axis.orient() =="?????M?M?d
NW?>ox?6?? ???"?b?_?=N?z?_vC?+[JE-jt&?BF	???S????{J??B]UZ?B~??????8??-?k?wi???????Fe??????N?????????i(l??O?0X??7????8???)???H??4??M????jA??S???5????U?	m,,]?/???'K???d?2???6?$?'?????ah???(???&?X????c?R?????i?2n?Ej&??Q~?]ng????fL,?t????)1??,E??bt@?n!?x?J??M???NPcxJ?Rk?+^?t`??&I???????)J?puc?L?.?y ??		???J:0????Z??j?9?-???{PLK??x???:?????????K?t????c??In0L???!?????@????qHCD?W'?R?a???H?-?|?E???  \??&S?,???:p\?8??T????????????u??"i?)??t?K?O??>{?:?$8:?C?%L?f#????*d?tE	?z?U1?X???R?,sK??{?p?=U'?>{OL?:<????jc?2Q????Y^?O'Rt?S?S????,`?????o?D?B?/Rj?t???]?????YU????A????W?C
??uq:??????:?fW????? Q1f.?DH?2??[???#}??H.R *?t???????~^ ???#???>d??A?+j?EF?????9t???????G???`n?????J?????h?t????$????K???????T? ?O??Wu??yV?5Dl????!??????p'iH????0?h???5X0?*p?9?? ow??????;?G`?v??m8?&c?/?I?N??k?????} M?e????
u?G??????????wp?=?u???D??@,???s?L??l???G?&???Q
??W?7S??????????1??????????}??>=m??N9M?R?_???Q?>>%???r%???~B<??????@??w"b???h??65?]1????TaybU?????A??????|?#Hu?,?e??b??G^??R???? ?N"???k??fL?g??o?2xX??<???r????u?y???Nz??D?5{	??3????`?F?9???E???8???2??Q ??^?K0?C5?1??a??b????1d???????_??L?G???`??C0	??}???9???<7$<)?9??Lm?T????Cy??G?)^?5Y??A???^????????-d?|??????%?-?????PA?xp???]??"??|/?C?5nm??Z}???M%????5D???	??'??l????#4????????? ???h??2]??????5??N?B????/:Fi.m?6?my?4?ermt?|?E~?B??U??T?B?B=?@???f^@#??R?Gp???M??(??tW???iw????..{?hg?|}???[?#J :o?_????6?h?{B??a?1B??5???Z?@xry???Zw???D?A?N???N?b?prP? ?q??L?j?e	g?!i?M?v????????;=Q????&,&?lC?v?./?2?!q#?@?|?? ?m7???w?0???????F_?3??A??/Y??g?y???L?]????'?zQ????L???@??#" g?~{??;%???X?d?GA???'w???????????r}????69??p?_?Q?$S?*???a?kJ?e?U??;??lIt?H???l?J?!?#???d[???8??8]m??????'e&O??1/;3"j??????
?nJ	??@b????2pC?8???? q?f??t(?!?4??`0?|d?f?Us]?<????6b????????????X o?H[?B/j3??F/:=???,???U?(-j?????Ka?E?????4??DW?9??_t?`?7?iR?jogI?<;?K0? &????{d??;?#x??????????????Ue?Z)n{???z??k?D??u??f???????a?,-?U?????4'?Z?HNG?W?~??O?~??A???4??J?|?zIBV}?M<?g&?,?UhtA
?i??m??pSe;%lx???ud??et??R???-:??#?RQ??????/u?*?w????"MlZ???B??A(w?5Jgr>?k?,t?%?A???C??????B?\x??7???k??^?mhS?ig??1([?;??k?_+J???D???,??P????S,I????B?	-????p	sLYw>5t???7??Fmw??m?}????c??]????5?????m$J:????C???A?C???m???h?<???
3?c?A?X?
? ?,????9??\V??mBMy??_???o?,????}?Ivw????"??/?tS??z@?z??SQnt 9?3UkHv?W?R?,2^V@(Z???A???f?#?Q??25p?(xl??j????v??????????Ml????7??U?";D??r?tSe5??X?	??>???(q?c ????|=?d??y8??Of?2%??	?X?)f??V????Z@???
??az?	???e??z6?x???]?J????!<?}?I?b?{????}w!?5?????#???#P9|?K?Q	??????????@?F?x??<sV{g???????{?;?@/?6???u???????X???*?K&k?o?????`????)????"? ?S?F?
?6?7J?Db?d.E/H!?~|?d?'??????S?????#??i$;]?@?L??????6??~$^??<h?b??????k???F???D????a??m?b????qr??s?jW<??a?8?h?ia?:xO??E,???aT?Sb[??J??!}?&??h??D??g??O?AAV?	?-?*sO2tF'E?Ct????(?Wo|{?P*?n?#j????????:x?n?`Yx/o??&??????>?&???q?Q???	gaA}a???5;?D??l??V?x?N?m?y?H?:???????fY???3N??Zy?|J??_@?l?|"??2?a8?@???}?0???Gq'N7??,???>qz2|HM?j]???sSD"????????aEU?w???X?=?x??9??k??{"J????~;??V?Zs????s?{&?Tg????GKg?Q.?"?q5<?
????R??'?q??X?U??S;???Yx??????g?yP|???=?? ?DFZ?K?L?????09?E?d5I????Yo,Q_??
TR??)?V?pO[??.??h^?B?/?????r?A~fs?Iu??\?#????S*,;9?r]??e?_NGJI??p??T?????>Kr???[?T??Fd;2??????e???1[0Dh7??_ct???W2????0O}W??????2??N????|?+??????(?????\??Dyz????5 ?(???d!?m?2.?????n????'!?B???????????r?
??J?iw??jXe??????????)?40?0??OE?VWt{???\??L??7?;??L?9????Z<e"?.?hqvUy?a??????U?????b???c?dJ?o?e?Av?n??????0???5??r?:x{xc???????????R??6?Q0"TxrR?????ER????,??b2?bN {O??uc?{.????i?`{??h?@??I?}k?J????????%?l?,{????????}2?:?????@;??L?T?k	?%?-.F?=!????j??g(?$????*{????S{e??W5?2?
.?`8??lhB?t??!]?_d\I????5??5=|(YP#'5?O?/|? bV??$'X????&?pDY=??=wP&?}j?????R_?9a?x1?O?2????Vr*?(??d)0?????f???]?W}??A?_?S*????Ua????K?????X????s??r!?`???M'??Vej??mM???oA]WJtW?Sy??4??9?G?U???&??c?M????>cB~?%??'?
/?-?????j#u????qMl;?$?'~?R?,?YF?8?}h??????F>r#???????_D?E?"???>???p??????B-Q?@?p?s??????Q?zS???*??l??ZV3????36??L?a ?]???
n??+?????q????????#?????>Pg.`"?Rw8?D?????mTf?????Q??????wi??Ck????b?}?1???l?%??? ???C??{??L??u?6}?e???.?ljE?PG?>,???g????[??????oB???h??{I	?z-??l	???N??6??/??;????Z??iP3s?????{@"???}~???2