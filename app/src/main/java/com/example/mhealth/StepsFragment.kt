package com.example.mhealth

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_steps.*
import java.util.concurrent.ThreadLocalRandom

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HeartFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HeartFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class StepsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createChart()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createChart () {
        val values = ArrayList<Entry>()
        for (i in 0..50) {
            values.add(Entry(i.toFloat(), ThreadLocalRandom.current().nextInt(100, 15000 + 1).toFloat()))
        }
        val lineData = LineDataSet (values, "Steps Taken")
        lineData.fillColor = Color.parseColor("#1976D2")
        lineData.color = Color.parseColor("#1976D2")
        lineData.lineWidth = 2f
        lineData.setDrawFilled(true)
        val drawable = context?.let { ContextCompat.getDrawable(it, R.drawable.chart_background) }
        lineData.fillDrawable = drawable
        lineData.valueTextSize = 0f
        lineData.setDrawValues(false)
        lineData.setDrawCircles(false)

        steps_Chart.data = LineData(lineData)
        steps_Chart.setDrawGridBackground(false)
        steps_Chart.setDrawBorders(false)
        steps_Chart.setDrawMarkers(false)
        steps_Chart.disableScroll()
        steps_Chart.axisLeft.axisMinimum = 20f //TODO - Average minus certain amount
        steps_Chart.xAxis.isEnabled = false
        steps_Chart.axisLeft.isEnabled = true
        steps_Chart.axisRight.isEnabled = false
        steps_Chart.description.text = ""
        steps_Chart.legend.isEnabled = false

        steps_Chart.axisLeft.setDrawAxisLine(false)
        steps_Chart.axisLeft.setDrawGridLines(false)
        steps_Chart.axisLeft.setDrawLabels(false)

        val averageLimit = LimitLine(6000f, "Target")
        averageLimit.lineWidth = 4f
        averageLimit.lineColor = Color.parseColor("#9E9E9E")
        averageLimit.enableDashedLine(30f, 10f, 0f)
        averageLimit.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        averageLimit.textSize = 15f

        steps_Chart.axisLeft.limitLines.add(0, averageLimit)
        steps_Chart.axisLeft.setDrawLimitLinesBehindData(true)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                StepsFragment().apply {
                    arguments = Bundle().apply {}
                }
    }
}
