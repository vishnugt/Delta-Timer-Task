using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using System.Threading;
using Windows.System.Threading;
using System.Threading.Tasks;
using System.Diagnostics;


// The Blank Page item template is documented at http://go.microsoft.com/fwlink/?LinkId=234238

namespace App5
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class MainPage : Page
    {
        public MainPage()
        {
            this.InitializeComponent();
            
        }

        /// <summary>
        /// Invoked when this page is about to be displayed in a Frame.
        /// </summary>
        /// <param name="e">Event data that describes how this page was reached.  The Parameter
        /// property is typically used to configure the page.</param>
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            
        }

        public static async Task anotherthread() 
        {
            
        }
        double sysmilli, sysseconds, sysminute;

        Stopwatch sw = new Stopwatch();
        private void Button_Click(object sender, RoutedEventArgs e)
        {
            
            sw.Start();
        }
        private void pausebtn_Click(object sender, RoutedEventArgs e)
        {
            sw.Stop();
            Double mintemp, sectemp,millitemp;
            mintemp = (sw.ElapsedMilliseconds) / 60000;
            sectemp = (sw.ElapsedMilliseconds) /1000;
            millitemp = sw.ElapsedMilliseconds % 1000;


            minblock.Text = (mintemp).ToString();
        milliblock.Text = (millitemp).ToString();
         secblock.Text = (sectemp).ToString();

        }
        int lapno = 0;
        String resultstore;
        private void resetbtn_Click(object sender, RoutedEventArgs e)
        {
            Double mintemp, sectemp, millitemp;
            mintemp = (sw.ElapsedMilliseconds) / 60000;
            sectemp = (sw.ElapsedMilliseconds) / 1000;
            millitemp = sw.ElapsedMilliseconds % 1000;


            minblock.Text = (mintemp).ToString();
            milliblock.Text = (millitemp).ToString();
            secblock.Text = (sectemp).ToString();

            sw.Reset();
            minblock.Text = "0";
            secblock.Text = "00";
            milliblock.Text = "000";
            resultstore += " Lap Number  " + (++lapno) + "   " + mintemp + " :" + sectemp + "  :" + millitemp + "\n\n";
            resultblock.Text = resultstore;

        }

        private void ListView_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {

        }
    }
}
