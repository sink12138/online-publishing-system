using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace ArticleManager
{
    public partial class SelectAuthor : Form
    {
        public string name;
        public bool operationSucceed = false;
        public int authorid;

        private void button1_Click(object sender, EventArgs e)
        {
            operationSucceed = false;
            this.Close();
        }

        private void UpdateAuthor_button_Click(object sender, EventArgs e)
        {
            UpdateAuthor_button.Enabled = false;
            operationSucceed = true;
            authorid = Convert.ToInt32(dataGridView1.CurrentRow.Cells[0].Value);
            name = dataGridView1.CurrentRow.Cells[1].Value.ToString();
            this.Close();
        }

        public SelectAuthor()
        {
            InitializeComponent();
            string Con = "Data Source=publish.vixerunt.cn,1433\\SQLEXPRESS;Initial Catalog=ArticleManagerDatabase;Persist Security Info=True;User ID=sa;Password=BUAAgjy1521";
            SqlConnection Sc = new SqlConnection(Con);
            SqlDataAdapter Sda = new SqlDataAdapter("select * from Author", Sc);
            DataSet Ds = new DataSet();
            Sda.Fill(Ds);
            DataTable Dt = Ds.Tables[0];
            dataGridView1.AutoGenerateColumns = true;
            dataGridView1.DataSource = Dt;
            dataGridView1.AllowUserToAddRows = false;
            dataGridView1.ReadOnly = true;
            dataGridView1.Columns[0].HeaderCell.Value = "ID";
            dataGridView1.Columns[1].HeaderCell.Value = "姓名";
            dataGridView1.Columns[2].HeaderCell.Value = "E-mail";
        }

        private void SelectAuthor_Load(object sender, EventArgs e)
        {
            this.FormBorderStyle = FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
        }
    }
}
